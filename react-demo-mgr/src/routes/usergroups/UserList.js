/**
 * Created by STZHANG on 2017/5/25.
 */
/**
 * Created by shenfu on 2017/5/16.
 */
import React, { PropTypes } from 'react'
import styles from './List.less'

// 采用antd的UI组件
import { Table, Row, Col } from 'antd'
import { Link } from 'dva/router'

const AccountTable = ({
                            pagination,
                            loading,
                            handleShowDetail,
                            handleRowsSelected,
                            selectedRowKeys,
                            accountList,
                            ...tableProps
                          }) => {
  const columns = [{
    title: '用户编号',
    dataIndex: 'userId',
    key: 'userId',
    width: 120,
    render: (text, record, index) => {
      return (
        <span>
                  <a onClick={() => handleShowDetail(record.accountId)}>{record.userId}</a>
                </span>
      )
    }
  }, {
    title: '身份类型',
    dataIndex: 'accountType',
    key: 'accountType',
    sorter: true,
    width: 120,
  }, {
    title: '用户名称',
    dataIndex: 'userName',
    key: 'userName',
    sorter: false,
    width: 120,
  }, {
    title: '性别',
    dataIndex: 'genderName',
    key: 'gender',
    sorter: false,
    width: 80,
  }, {
    title: '部门名称',
    dataIndex: 'departName',
    width: 160,
  }, {
    title: '账户状态',
    dataIndex: 'enabled',
    width: 80,
    render: (text, record) => {
       return (record.enabled === true)? "正常": "停用";
    }
  },

  ]


  const rowSelection = {
    onChange: (rowKeys, rows) => {
      console.log(`selectedRowKeys: ${rowKeys}`, 'selectedRows: ', rows);
      handleRowsSelected(rowKeys);
    },
    selectedRowKeys
  };

  return (
    <Row>
      <Col span={24}>
        <Table

          rowSelection={rowSelection}
          {...tableProps}
          bordered
          className={styles.table}
          rowKey="accountId"
          dataSource={accountList}
          columns={columns}
          simple
          pagination={pagination}
          loading={loading}
          scroll={{ y: 500 }}
        />
      </Col>
    </Row>
  )
}

AccountTable.propTypes = {
  pagination: PropTypes.object,
  selectedRowKeys: PropTypes.array,
  loading: PropTypes.bool,
  handleShowDetail: PropTypes.func,
  accountList: PropTypes.array,
  handleRowsSelected: PropTypes.func,
}

export default AccountTable
