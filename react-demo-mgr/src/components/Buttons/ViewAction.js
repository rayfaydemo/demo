/**
 * Created by shenfu on 2017/6/20.
 */
import React from 'react'
import PropTypes from 'prop-types'
import { Link } from 'dva/router'

class ViewAction extends React.Component {
  render () {
    const { to, display, disabled } = this.props
    return (
      <Link to={to} disabled={disabled}>{display}</Link>
    )
  }
}

ViewAction.defaultProps = {
  display: '详细',
  disabled: false,
}

ViewAction.propTypes = {
  to: PropTypes.string.isRequired,
  display: PropTypes.string,
  disabled: PropTypes.bool,
}

export default ViewAction
