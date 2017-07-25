/**
 * Created by shenfu on 2017/6/16.
 */
import React from 'react'

function wapperComponentsLifecycle ({ DidMount }) {
  return (ComposedComponent) => {
    return class Wapper extends React.Component {
      componentWillUnmount () {

      }

      componentDidMount () {
        if (typeof (DidMount) !== 'undefined') {
          DidMount({ props: this.props })
        }
      }

      render () {
        return <ComposedComponent {...this.props} />
      }
    }
  }
}

export default wapperComponentsLifecycle
