/**
 * Created by STZHANG on 2017/5/15.
 */
import SearchTree from './SearchTree';
import EditableFormItem from './EditableFormItem'
import { connect } from 'dva';
import classnames from 'classnames';
import styles from './index.less'

import { Row, Col, Form, Button, Input } from 'antd';
const ButtonGroup = Button.Group;
const FormItem = Form.Item;
const MenuMgrPage = ({ mgrMenus, dispatch}) => {
  const { menus, menuInfo, menuPrivileges, formStatus, formEditable } = mgrMenus
  const handleNodeClick = (selectMenus)=> {
    if (selectMenus.length > 0){
      let menuId = selectMenus[0];
      dispatch({ type: 'mgrMenus/showMenu', payload: { menuId } })
    }
  }

  const handleRefresh = ()=> {
    dispatch({ type: 'mgrMenus/query'})
  }

  const handleFormChange = (v, k, f, s) => {
    if (s === 'save') {
         dispatch({type: 'mgrMenus/saveMenu', payload: {"menuId": k, "value": v, "fieldName": f}})
     }
  }
  const toEditModel = () => {
    dispatch({ type: 'mgrMenus/toEditModel' })
  }
  const saveEditForm = () =>{
    dispatch({ type: 'mgrMenus/saveEditForm' })
  }
  const cancelEditForm = () =>{
    dispatch({ type: 'mgrMenus/cancelEditForm' })
  }

  const searchTreeProps = {dataSource: menus, handleNodeClick, handleRefresh}

  const formItemLayout = {
    labelCol: { span: 6 },
    wrapperCol: { span: 14},
  };

  return (
    <div className="content-inner">
      <Row>
        <Col span={8}>
          <SearchTree {... searchTreeProps} />
        </Col>
        <Col span={8} offset={1}>
          <div className={classnames(styles.borderdiv)} >
            <div className={classnames(styles.rightAlignDiv)} >
            {formEditable ?
              <ButtonGroup>
                <Button type="primary" onClick={saveEditForm}>保存</Button>
                <Button onClick={cancelEditForm}>取消</Button>
              </ButtonGroup>
              : <Button icon="edit" onClick={toEditModel} >编辑</Button>
            }
            </div>
            <Form>
              <FormItem label="菜单标识" {...formItemLayout} key="menuId">
                <span>{menuInfo.ekey}</span>
              </FormItem>
              <FormItem label="菜单名称" {...formItemLayout} key="menuName">
                <EditableFormItem status={formStatus} value={menuInfo.title? menuInfo.title: ''}
                                  editable={formEditable} onChange={(value, status) => handleFormChange(value, menuInfo.key, 'title', status)} />
              </FormItem>
              <FormItem label="菜单排序" {...formItemLayout} key="menuIndex">
                <EditableFormItem status={formStatus} value={menuInfo.index? menuInfo.index: ''} isFieldNumber={true}
                                  editable={formEditable} onChange={(value, status) => handleFormChange(value, menuInfo.key, 'index', status)} />
              </FormItem>
              <FormItem label="菜单链接" {...formItemLayout} key="menuLink">
                <span>{menuInfo.link}</span>
              </FormItem>
            </Form>

            <div>权限配置</div>
            <div>
              {
                menuPrivileges.map(function(item){
                  return (<span key={item.roleId}>{item.roleName}({item.roleId});</span>)
                })
              }
            </div>

          </div>
        </Col>
      </Row>
    </div>
  )
}

export default connect(({mgrMenus, dispath}) => ({mgrMenus, dispath}))(MenuMgrPage)
