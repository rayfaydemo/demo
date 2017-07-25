/**
 * Created by shenfu on 2017/6/20.
 */
import React from 'react'
import PropTypes from 'prop-types'
import { Popconfirm } from 'antd'

class DeleteAction extends React.Component {
  constructor (props) {
    super(props)
    this.handOnClick = this.handOnClick.bind(this)
  }

  handOnClick = () => {
    this.props.onClick()
  }

  render () {
  	const { display, disabled, custTitle } = this.props
    return (
      <Popconfirm
        title={custTitle}
        onConfirm={() => this.handOnClick()}
        okText="是"
        cancelText="否"
      >
        <a href="#" style={!disabled ? { color: 'red', whiteSpace: 'nowrap' } : { whiteSpace: 'nowrap' }} disabled={disabled}>{display}</a>
      </Popconfirm>
    )
  }
}

DeleteAction.defaultProps = {
  disabled: false,
  display: '删除',
  custTitle: '是否确定要删除这条数据？',
}

DeleteAction.propTypes = {
  onClick: PropTypes.func.isRequired,
  disabled: PropTypes.bool,
  display: PropTypes.string,
  custTitle: PropTypes.object,
}

export default DeleteAction
