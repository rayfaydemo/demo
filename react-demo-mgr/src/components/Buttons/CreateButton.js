/**
 * Created by shenfu on 2017/6/20.
 */
import React from 'react'
import PropTypes from 'prop-types'
import { Button } from 'antd'

class CreateButton extends React.Component {
  constructor (props) {
    super(props)
    this.handOnClick = this.handOnClick.bind(this)
  }

  handOnClick = () => {
    this.props.onClick()
  }

  render () {
    const { disabled, displayString, size, style } = this.props
    return (
        <Button type="primary" size={size} onClick={this.handOnClick} icon="plus" disabled={disabled} style={style}>新增{displayString}</Button>
    )
  }
}

CreateButton.defaultProps = {
  disabled: false,
  size: 'default',
  style: {},
}

CreateButton.propTypes = {
  onClick: PropTypes.func.isRequired,
  disabled: PropTypes.bool,
  displayString: PropTypes.string.isRequired,
  size: PropTypes.string,
  style: PropTypes.object,
}

export default CreateButton
