/**
 * Created by STZHANG on 2017/5/27.
 */
import { Select, Spin, Button } from 'antd';
const Option = Select.Option;
import React, { PropTypes } from 'react'

class UserSelectSearch extends React.Component {
  constructor(props) {
    super(props);
    this.lastFetchId = 0;
  }
  state = {
    data: [],
    value: [],
    fetching: false,
  }
  fetchUser = (value) => {
    let fetchUserFunc = this.props.fetchUserFunc;
    this.lastFetchId += 1;
    const fetchId = this.lastFetchId;
    console.log('fetching user', value);
    this.setState({ fetching: true });
    return fetchUserFunc(value).then((data) => {
          if (fetchId !== this.lastFetchId) { // for fetch callback order
            return;
          }
          if (data ) {
            this.setState({data,  fetching: false});
          }
        }
    );
  }

  handleChange = (value) => {
    this.setState({
      value,
      fetching: false,
    });
  }


  handleButtonClick = () => {
   let resul = this.props.handleOnChange(this.state.value);
    if(resul){
      this.setState({
        value: [],
        data: [],
        fetching: false,
      });
    }
  }

  render() {
    const { fetching, data, value } = this.state;
    return (
    <span style={{ width: 420, marginLeft: 20}}>
      <Select
        mode="multiple"
        labelInValue
        value={value}
        placeholder="请选择要添加的用户"
        filterOption={false}
        onSearch={this.fetchUser}
        onChange={this.handleChange}
        style={{ width: 360}}
        optionLabelProp="display"
        allowClear
      >
        {data.map(d => <Option display={d.userName + " | " + d.departName} value={d.userId+"$"+d.accountType+"$"+d.accountId} key={d.userId}>{d.userId} | {d.userName} | {d.genderName} | {d.accountType} | {d.departName}</Option>)}
      </Select>
      <Button type="primary" className="" style={{marginLeft: 6}} onClick={this.handleButtonClick}>添加</Button>
    </span>
    );
  }
}


UserSelectSearch.propTypes = {
  fetchUserFunc: PropTypes.func,
  handleOnChange: PropTypes.func,
}


export default UserSelectSearch;
