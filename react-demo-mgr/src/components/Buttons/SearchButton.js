/**
 * Created by shenfu on 2017/6/20.
 */
import React from 'react'
import PropTypes from 'prop-types'
import { Button } from 'antd'

class SearchButton extends React.Component {
  constructor (props) {
    super(props)
    this.handOnClick = this.handOnClick.bind(this)
  }

  handOnClick = () => {
    this.props.onClick()
  }

  render () {
    const { loading, disabled, size, style } = this.props
    return (
        <Button type="primary" onClick={this.handOnClick} loading={loading} icon="search" size={size} style={style} disabled={disabled}>查询</Button>
    )
  }
}

SearchButton.defaultProps = {
  disabled: false,
  size: 'default',
  style: { marginLeft: '5px', marginRight: '5px' },
}

SearchButton.propTypes = {
  onClick: PropTypes.func.isRequired,
  loading: PropTypes.bool.isRequired,
  disabled: PropTypes.bool,
  size: PropTypes.string,
  style: PropTypes.object,
}

export default SearchButton
