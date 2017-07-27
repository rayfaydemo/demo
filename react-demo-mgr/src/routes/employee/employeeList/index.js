import React, { PropTypes } from 'react'
import { connect } from 'dva'
import { Col, Input, Row, Button } from 'antd'
import EmployeeListTable from './employeeListTable'


// 定义画面接口参数，其中employeeList就是我们在model里面设定的namespace
const EmployeeList = ({ location, dispatch, employeeList, loading, app }) => {
  // 展开state中的属性
  const { employeeTableDataSource, employeeTableLoading, txtEmployeeName, pagination } = employeeList

  // 传递给表格组件的属性
  const tableProp = {
    dispatch,
    employeeTableDataSource,
    employeeTableLoading,
    pagination,
  }

  const onChange = (e) => {
    dispatch({
      type: 'employeeList/onChangeEmployeeName',
      payload: {
        txtEmployeeName: e.target.value,
      },
    })
  }

  const onClick = () => {
    dispatch({
      type: 'employeeList/getEmployeeTableDataSource',
      payload: {
        txtEmployeeName,
      },
    })
  }

  return (
    <div className="content-inner">
      <Row gutter={24}>
        <Col md={{ span: 6 }}>
          <Input placeholder="职员姓名" value={txtEmployeeName} onChange={onChange} />
        </Col>
        <Col md={{ span: 6 }}>
          <Button type="primary" icon="search" onClick={onClick}>查询</Button>
        </Col>
      </Row>
      <Row gutter={24} style={{ marginTop: '10px' }}>
        <Col span={24}>
          <EmployeeListTable {...tableProp} />
        </Col>
      </Row>
    </div>
  )
}

// 定义画面可传入的属性以及属性类型
EmployeeList.propTypes = {
  location: PropTypes.object,
  dispatch: PropTypes.func,
  employeeList: PropTypes.object,
  loading: PropTypes.object,
  app: PropTypes.object,
}

// 导出并关联Model中state到本画面的props
export default connect(({ employeeList, loading, app }) => ({ employeeList, loading, app }))(EmployeeList)
