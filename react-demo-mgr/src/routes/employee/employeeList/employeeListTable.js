import React, { PropTypes } from 'react'
import { Table } from 'antd'

const EmployeeListTable = ({ dispatch, employeeTableDataSource, employeeTableLoading, pagination }) => {
  // 定义表格列
  const columns = [
    {
      title: '姓名',
      dataIndex: 'name',
      width: 100,
    },
    {
      title: '电话',
      dataIndex: 'phone',
      width: 100,
    },
    {
      title: '年龄',
      dataIndex: 'age',
      width: 100,
    },
    {
      title: '地址',
      dataIndex: 'address',
      width: 100,
    },
    {
      title: '性别',
      dataIndex: 'isMale',
      width: 100,
    },
    {
      title: 'email',
      dataIndex: 'email',
      width: 100,
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      width: 100,
    },
  ]

  // 分页操作
  const onChange = (page) => {
    dispatch({
      type: 'employeeList/getEmployeeTableDataSource',
      payload: {
        currentPage: page.current,
        pageSize: page.pageSize,
      },
    })
  }

  // 渲染表格
  return (
    <Table bordered
      dataSource={employeeTableDataSource}
      columns={columns}
      onChange={onChange}
      rowKey="id"
      pagination={pagination}
      loading={employeeTableLoading}
    />
  )
}

EmployeeListTable.propTypes = {
  dispatch: PropTypes.func,
  employeeTableDataSource: PropTypes.array,
  pagination: PropTypes.object,
  employeeTableLoading: PropTypes.bool,
}

export default EmployeeListTable
