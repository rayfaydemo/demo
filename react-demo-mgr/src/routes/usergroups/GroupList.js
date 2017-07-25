/**
 * Created by STZHANG on 2017/6/22.
 */
import React, { PropTypes } from 'react'
import styles from './List.less'

// 采用antd的UI组件
import { Table, Row, Col } from 'antd'
import { Link } from 'dva/router'

const GroupList = ({
                        pagination,
                        loading,
                        handleEditDetail,
                        handleEditAccounts,
                        handleEditRoles,
                        handleRowsSelected,
                        selectedRowKeys,
                        userGroupList,
                        ...tableProps
                      }) => {
  const columns = [{
    title: '用户组标识',
    dataIndex: 'groupCode',
    key: 'groupCode',
    sorter: false,
    width: 150,
  }, {
    title: '用户组名称',
    dataIndex: 'groupName',
    key: 'groupName',
    sorter: false,
    width: 80,
  }, {
    title: '类型',
    dataIndex: 'groupTypeName',
    key: 'types',
    sorter: true,
    width: 150,
  }, {
    title: '角色列表',
    key: 'userRoleNames',
    sorter: false,
    width: 120,
    render: (text, record, index) => {
      return (
         <div>
           {
             record.userRoleNames && record.userRoleNames.map(item =>
                 <div>{item}</div>
             )
           }
         </div>
      )
    }
  }, {
    title: '操作',
    dataIndex: 'operation',
    key: 'peration',
    sorter: false,
    width: 120,
    render: (text, record, index) => {
      return (
        <span>
         <a onClick={() => handleEditDetail(record.groupId)}>编辑</a>
          <span className="ant-divider" />
         <a onClick={() => handleEditRoles(record.groupId)}>编辑权限</a>
           <span className="ant-divider" />
         <a onClick={() => handleEditAccounts(record.groupId)}>编辑用户</a>
        </span>
      )
    },
  }]


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
          rowKey="groupId"
          dataSource={userGroupList}
          columns={columns}
          pagination={pagination}
          loading={loading}
          scroll={{ y: 500 }}
        />
      </Col>
    </Row>
  )
}

GroupList.propTypes = {
  pagination: PropTypes.object,
  selectedRowKeys: PropTypes.array,
  loading: PropTypes.bool,
  handleShowDetail: PropTypes.func,
  userGroupList: PropTypes.array,
  handleRowsSelected: PropTypes.func,
}

export default GroupList
