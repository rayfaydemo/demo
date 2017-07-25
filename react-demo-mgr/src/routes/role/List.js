/**
 * Created by shenfu on 2017/5/16.
 */
import React, { PropTypes } from 'react'
import styles from './List.less'
import DetailForm from './DetailForm'


// 采用antd的UI组件
import { Table, Row, Col, Button, message ,Modal} from 'antd'
const confirm = Modal.confirm;

const RoleTable = ({
  pagination,
  loading,
  resultList,
  roleTypeList,
  visible,
  dispatch,
  searchData,
  formData,
  selectRole,
  editDisabled,
  treeEdit,
  ...tableProps
}) => {
  const columns = [
    {
      title: '角色标识',     // table header 文案
      dataIndex: 'roleId', // 数据对象内的属性，也做react vdom 的key
      type: 'string',     // table 内显示的类型
      sorter: true,         // 是否需要排序
      width: 200,
    }, {
      title: '角色名称',
      dataIndex: 'roleName',
      type: 'string',
      width: 200,
    }, {
      title: '角色类型',
      dataIndex: 'typeName',
      type: 'string',
      width: 200,
    },{
      title: '操作',
      width: 200,
      render: (text, record) => {

        return  (
          <div className="editable-row-operations">
            {
              <span>
               <a onClick={() => editHandle(record)}>修改</a>
                <span className="ant-divider" />
                <a onClick={() => roleConfigHandle(record)}>配置权限</a>
               </span>
            }
          </div>
        );

      }

    },

  ]

  const rowSelection = {
    onChange: (selectedRowKeys, selectedRows) => {
      dispatch({
        type: 'roleList/setSelect',
        payload: {
          selectRow: selectedRowKeys,
        },
      })
    },
  }


  const addHandle = () => {
    dispatch({
      type: 'roleList/showModal',
      payload: {
        modalType: 'create',
      },
    })
  }

  const editHandle = (record) => {
    if (record.roleId === '') {
      message.error('请选择需要编辑的记录！')
    } else {
      dispatch({
        type: 'roleList/showModal',
        payload: {
          modalType: 'update',
          roleId: record.roleId,
        },
      })
    }
  }


  function showConfirm() {

    if (selectRole === '') {
      message.error('请选择需要删除的记录！')
      return
    }
    confirm({
      title: '是否确认删除?',
      onOk() {
          dispatch({
            type: 'roleList/delRole',
            payload: {
              queryData: searchData.queryData,
              currentPage: pagination.current,
              pageSize: pagination.pageSize,
              sorter: searchData.sorter,
              roleId: selectRole,
            },
          })

      },
      onCancel() {
        console.log('Cancel');
      },
    });
  }




  const onChange = (page, filters, sorter) => {
    dispatch({
      type: 'roleList/queryRole',
      payload: {
        currentPage: page.current,
        pageSize: page.pageSize,
        sort: {
          sortField: sorter.columnKey,
          sortOrder: sorter.order,
        },
        queryData: searchData.queryData,
      },
    })
  }


  const roleConfigHandle = (record) => {

      if(treeEdit){
        confirm({
          title: '角色权限修改后未保存,是否配置新权限?',
          onOk() {
            dispatch({
              type: 'roleList/getRolePrivileges',
              payload: {
                record,
              },
            })
          },
          onCancel() {

          },
        });
      }else {
        dispatch({
          type: 'roleList/getRolePrivileges',
          payload: {
            record,
          },
        })
      }
  }


  const detailProps = {
    roleTypeList,
    visible,
    dispatch,
    searchData,
    pagination,
    formData,
    editDisabled,
  }

  return (
    <Row>
      <Col span={24}>
          <div style={{ marginBottom: '12px' }}>
            <Button type="primary" size="large" className="margin-right" onClick={addHandle} loading={loading}>新增</Button>
            <Button size="large" className="margin-right" onClick={showConfirm}>删除</Button>
            <DetailForm {...detailProps} />
        </div>
      </Col>

      <Col span={24}>
        <Table
          {...tableProps}
          bordered
          className={styles.table}
          dataSource={resultList}
          columns={columns}
          simple
          pagination={pagination}
          loading={loading}
          scroll={{ y: 500 }}
          rowSelection={rowSelection}
          onChange={onChange}
          rowKey={'roleId'}
        />
      </Col>
    </Row>
  )
}

RoleTable.propTypes = {
  pagination: PropTypes.object,
  loading: PropTypes.bool,
  resultList: PropTypes.array,
  dispatch: PropTypes.func,
  roleTypeList: PropTypes.array,
  visible: PropTypes.bool,
  searchData: PropTypes.object,
  formData: PropTypes.object,
  selectRole: PropTypes.string,
  editDisabled: PropTypes.bool,
  treeEdit: PropTypes.bool,
}

export default RoleTable
