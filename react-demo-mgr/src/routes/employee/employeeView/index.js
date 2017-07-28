import React, { PropTypes } from 'react'
import { connect } from 'dva'
import { Col, Row, Button, Icon } from 'antd'

const EmployeeView = ({ dispatch, employeeView }) => {
  const { employee } = employeeView

  const showCreateModal = () => {
    history.back()
  }

  return (
    <div className="content-inner">
      <Row gutter={24}>
        <Col md={{ span: 6 }}>
          职员ID：{employee.id !== null ? employee.id : <Icon type="loading" />}
        </Col>
      </Row>
      <Row gutter={24}>
        <Col md={{ span: 6 }}>
          职员姓名：{employee.name !== null ? employee.name : <Icon type="loading" />}
        </Col>
      </Row>
      <Row gutter={24}>
        <Col md={{ span: 6 }}>
          职员性别：{employee.sex !== null ? employee.sex === '1' ? '男' : '女' : <Icon type="loading" />}
        </Col>
      </Row>
      <Row gutter={24}>
        <Col md={{ span: 6 }}>
          职员电话：{employee.telNumber !== null ? employee.telNumber : <Icon type="loading" />}
        </Col>
      </Row>
      <Row gutter={24}>
        <Col md={{ span: 6 }}>
          职员地址：{employee.address !== null ? employee.address : <Icon type="loading" />}
        </Col>
      </Row>
      <Row gutter={24}>
        <Col md={{ span: 6 }}>
          <Button icon="arrow-left" type="primary" onClick={showCreateModal}>返回</Button>
        </Col>
      </Row>
    </div>
  )
}

EmployeeView.propTypes = {
  dispatch: PropTypes.func,
  employeeView: PropTypes.object,
}

export default connect(({ employeeView }) => ({ employeeView }))(EmployeeView)
