import React from 'react'
import PropTypes from 'prop-types'
import { Card, Col, Row,message,Icon} from 'antd'
import { Link } from 'dva/router'
import './CardList.less'

class CardList extends React.Component {
  constructor (props) {
    super(props)
  }

  loadMore = () => {
    const pager = this.props.pagination;
    if(pager.current * pager.pageSize >= pager.total )
    {
      message.info("已全部加载完毕！")
      return;
    }
    pager.current = pager.current + 1;
    if (this.props.onLoadMore) {
      this.props.onLoadMore(pager);
    }
  }

  click = (e) => {
    var id =  e.currentTarget.id;
    let selectedCards = this.props.selectedCards;
    var index = -1;
    for(var i = 0; i < selectedCards.length; i++) {
      if(selectedCards[i] === id )
      {
        index=i;
        break;
      }
    }
    if(index === -1)
    {
      selectedCards.push(id);
    }
    else
    {
      selectedCards.splice(index,1);
    }
    if (this.props.onSelectedChange) {
      this.props.onSelectedChange(selectedCards);
    }
  }

  checkSelected = (id) => {
    var display = 'none'
    let selectedCards = this.props.selectedCards;
    var index = -1;
    for(var i = 0; i < selectedCards.length; i++) {
      if(selectedCards[i] === id )
      {
        index=i;
        break;
      }
    }
    if(index > -1)
    {
      display = 'inline-block'
    }
    return display;
  }

  render () {
    var list = () => {
      const { cardKey,colNum,titleCol,contentCol,dataSource,selectedCards,moreDisplay,moreLink } = this.props;
      let colSpan = parseInt(24/colNum);
      var res = [];
      for(var i = 0; i < dataSource.length; i++) {
        var displayStyle = this.checkSelected(dataSource[i][cardKey]);
        var linkUrl = moreLink + '/' + dataSource[i][cardKey];
        res.push(
          <Col key={i} span={colSpan} >
            <div id={dataSource[i][cardKey]} style={{padding: '8px'}} onClick={this.click}>
              <Card title={<div><Icon style={{display:displayStyle,color: '#FF359A',fontSize: 25 }} type="check-circle" />{dataSource[i][titleCol]}</div>}
                  bordered={true} bodyStyle={{backgroundColor: "#FBFBFF"}}
                  extra={<Link to={linkUrl}>{moreDisplay}</Link>}>
                <div style={{wordwrap: 'normal',height:'40px'}}>
                  {dataSource[i][contentCol]}
                </div>
              </Card></div></Col>
        )
      }
      return res;
    }
    return (
       <div>
          <Row >
            {list()}
          </Row>
         <a style={{float:"right" }} onClick={this.loadMore}>加载更多</a>
       </div>
    )
  }
}


CardList.propTypes = {
  cardKey: PropTypes.string,
  dataSource: PropTypes.array,
  selectedCards:PropTypes.array,
  colNum:PropTypes.number,
  onSelectedChange:PropTypes.func,
  onLoadMore:PropTypes.func,
}

export default CardList
