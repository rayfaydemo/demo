/**
 * Created by shenfu on 2017/6/20.
 */
import React from 'react'
import PropTypes from 'prop-types'
import { Button, Modal } from 'antd'

const confirm = Modal.confirm

class DeleteButton extends React.Component {
  constructor (props) {
    super(props)
    this.handOnClick = this.handOnClick.bind(this)
  }

  handOnClick = () => {
    confirm({
      title: '是否确定要删除这些数据？',
      content: '删除的数据将无法恢复！',
      onOk: () => {
        this.props.onClick()
      },
      okText: '是',
      cancelText: '否',
    })
  }

  render () {
    const { disabled, size, style } = this.props
    return (
      <Button size={size}
        type="danger"
        onClick={this.handOnClick}
        icon="delete"
        disabled={disabled}
        style={style}
      >删除</Button>
    )
  }
}

DeleteButton.defaultProps = {
  disabled: false,
  size: 'default',
  style: {},
}

DeleteButton.propTypes = {
  onClick: PropTypes.func.isRequired,
  disabled: PropTypes.bool,
  size: PropTypes.string,
  style: PropTypes.object,
}

export default DeleteButton
