/**
 * Created by shenfu on 2017/6/20.
 */
import React from 'react'
import PropTypes from 'prop-types'
import { Button } from 'antd'

class ResetButton extends React.Component {
  constructor (props) {
    super(props)
    this.handOnClick = this.handOnClick.bind(this)
  }

  handOnClick = () => {
    this.props.onClick()
  }

  render () {
    const { disabled, size, style } = this.props
    return (
        <Button onClick={this.handOnClick} icon="rollback" size={size} style={style} disabled={disabled}>重置</Button>
    )
  }
}

ResetButton.defaultProps = {
  disabled: false,
  size: 'default',
  style: { marginLeft: '5px', marginRight: '5px' },
}

ResetButton.propTypes = {
  onClick: PropTypes.func.isRequired,
  disabled: PropTypes.bool,
  size: PropTypes.string,
  style: PropTypes.object,
}

export default ResetButton
