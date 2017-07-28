import React, { PropTypes } from 'react'
import { Table, Popconfirm } from 'antd'
import { Link } from 'dva/router'

const EmployeeListTable = ({ dispatch, employeeTableDataSource, employeeTableLoading, pagination, editEmployee, delEmployee }) => {
  // 定义表格列
  const columns = [
    {
      title: '姓名',
      dataIndex: 'name',
      width: 100,
    },
    {
      title: '性别',
      dataIndex: 'sex',
      width: 100,
      render: (text, record) => (<span>{record.sex === '1' ? '男' : '女'}</span>),
    },
    {
      title: '电话',
      dataIndex: 'telNumber',
      width: 100,
    },
    {
      title: '地址',
      dataIndex: 'address',
      width: 100,
    },
    {
      title: '操作',
      width: 100,
      render: (text, record) => (
        <div>
          <a onClick={() => editEmployee(record)}>编辑</a>
          <span className="ant-divider" />
          <Popconfirm title="是否确认删除该职员?" onConfirm={() => delEmployee(record.id)} okText="是" cancelText="否">
            <a href="#" style={{ color: 'red' }}>删除</a>
          </Popconfirm>
          <span className="ant-divider" />
          <Link to={`/employee/employeeView/${record.id}`}>查看</Link>
        </div>
      ),
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
  editEmployee: PropTypes.func,
}

export default EmployeeListTable
