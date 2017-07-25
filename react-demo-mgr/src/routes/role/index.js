/**
 * Created by shenfu on 2017/5/16.
 */
import React, { PropTypes } from 'react'
import { connect } from 'dva'

import RoleTable from './List'
import Filter from './Filter'
import SearchTree from './SearchTree'
import { Row, Col,message,Card } from 'antd'
const RoleManager = ({ location, dispatch, roleList }) => {
  const { menus, pagination, loading, resultList, roleTypeList, visible, searchData, formData, selectRole, editDisabled, selectTreeNode, selectRecord,display ,treeEdit} = roleList

  const filterProps = {
    resultList,
    roleTypeList,
    loading,
    pagination,
    filter: {
      ...location.query,
    },
    onFilterChange (value, page) {
      dispatch({
        type: 'roleList/queryRole',
        payload: {
          queryData: value,
          currentPage: 0,
          pageSize: page.pageSize,
          sorter: {},
        },
      })
    },
  }

  const roleListProps = {
    pagination,
    loading,
    resultList,
    roleTypeList,
    visible,
    searchData,
    formData,
    selectRole,
    editDisabled,
    treeEdit,
    dispatch,
  }

  const handleNodeClick = (selectMenus) => {
    // if (selectMenus.length > 0) {
    //   let menuId = selectMenus[0]
    //   dispatch({ type: 'role/showMenu', payload: { menuId } })
    // }
  }

  const handleRefresh = () => {
    dispatch({ type: 'roleList/queryMenu' })
  }

  const handleNodeCheck = (checkedKeys, info) => {
    console.log(checkedKeys)
    dispatch({ type: 'roleList/setChecks', payload: { checks: checkedKeys } })
  }

  const saveHandel = (e) => {
    let roleId = selectRecord.roleId
     if(roleId)    {
       dispatch({ type: 'roleList/saveRolePrivileges',
         payload: {
           roleId,
           selectTreeNode,
         } })

     }else{
      message.error("请选择需要设置菜单权限的角色！")
     }

  }




  const searchTreeProps = { dataSource: menus, handleNodeClick, handleRefresh, handleNodeCheck, saveHandel, selectTreeNode, selectRecord }

  return (
    <div className="content-inner">
        <Row>
          <Col span={display.tableWidth}>
              <Filter {...filterProps} />
              <RoleTable {...roleListProps} />
          </Col>
          <Col span={display.margin}></Col>
          <Col span={display.treeWidth} >
            <div title='菜单权限' style={{display:display.style}}>
              <h2>&nbsp;&nbsp;菜单权限-{selectRecord.roleName}&nbsp;&nbsp;</h2>
              <SearchTree {... searchTreeProps} ></SearchTree>
            </div>

          </Col>
        </Row>
    </div>
  )
}

function mapStateToProps ({ roleList }) {
  return { roleList }
}

RoleManager.propTypes = {
  location: PropTypes.object,
  dispatch: PropTypes.func,
  roleList: PropTypes.object,
}

export default connect(mapStateToProps)(RoleManager)

