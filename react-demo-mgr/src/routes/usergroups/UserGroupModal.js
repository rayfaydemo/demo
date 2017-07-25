/**
 * Created by STZHANG on 2017/6/22.
 */

import React from 'react'
import PropTypes from 'prop-types'
import { Form, Input, Select, Modal, Cascader } from 'antd'
const Option = Select.Option;
const FormItem = Form.Item

const formItemLayout = {
  labelCol: {
    span: 6,
  },
  wrapperCol: {
    span: 14,
  },
}

const DetailModal = ({
                       item = {},
                       handleCancel,
                       handleSaveOk,
                       groupTypeList,
                       groupModalStatus,
                       ...modalProps,
                       dispatch,
                       form: {
                         getFieldDecorator,
                         getFieldsValue,
                         setFieldsValue,
                         getFieldValue,
                         validateFields,
                         resetFields,
                       },
                     }) => {


 const  {editDisabled, urlShown, oldValueOfUrl} = groupModalStatus;

 const onCancel = () => {
   // cancel fields.
   handleCancel(resetFields);
 }

 const  onOK = () => {
   let fields = getFieldsValue();
   if (item.groupId) {
      fields.groupId = item.groupId;
   }
   validateFields((errors) => {
     if (errors) {
       return false;
     }
     handleSaveOk(fields);
   });
 }


  const handleSelectChanged = (v) =>{
     if(v == 'GROUP_DYNAMIC'){
       setFieldsValue({dynamicUrl: oldValueOfUrl});
       dispatch({
         type: 'userGroup/urlShown',
       })
     }else { //statistc
       let url = getFieldValue('dynamicUrl');
       dispatch({
         type: 'userGroup/oldValueOfUrl',
         payload: {oldValueOfUrl: url}
       })
       dispatch({
         type: 'userGroup/urlHide',
       })
     }
  }

  const modalOpts = {
    ...modalProps,
    onCancel: onCancel,
    onOk: onOK,
  }

  return (
    <Modal {...modalOpts}>
      <Form >
        <FormItem {...formItemLayout} label="用户组标识" hasFeedback>
          {getFieldDecorator('groupCode', {
            rules: [{
              required: true, message: '请输入用户组标识!',
            }],
            initialValue: item.groupCode,
          })(<Input readOnly={editDisabled} />)}

        </FormItem>


        <FormItem {...formItemLayout} label="用户组名称" hasFeedback>
          {getFieldDecorator('groupName', {
            rules: [{
              required: true, message: '请选择角色名称!',
            }],
            initialValue: item.groupName,
          })(<Input />)}

        </FormItem>


        <FormItem {...formItemLayout} label="用户组类型" hasFeedback>
          {getFieldDecorator('groupType', {
            rules: [{
              required: true, message: '请选择用户组类型!',
            }],
            initialValue: item.groupType?item.groupType:'GROUP_STATIC',
          })(<Select placeholder="请选择用户组类型"  onChange={handleSelectChanged} disabled={editDisabled} >
            {groupTypeList.map(option => <Option key={option.typeId} value={option.typeId}>{option.typeName}</Option>)}
          </Select>)}

        </FormItem>


        <FormItem {...formItemLayout} label="动态地址" hasFeedback
                  extra="动态地址只对动态用户组启用，动态地址是一个RestAPI
           ， 通过Get方法访问，返回当前用户是否属于某一类用户。
           例如： http://peronal.apps.cftest.net/personal/isChecker
           返回为success:true,  flag: false|true">
          {getFieldDecorator('dynamicUrl', {
            rules: [{
              required: false,
            }],
            initialValue: item.dynamicUrl,
          })(<Input disabled={!urlShown}/>)}
        </FormItem>

      </Form>
    </Modal>
  )
}

DetailModal.propTypes = {
  item: PropTypes.object,
  groupTypeList: PropTypes.array,
  handleCancel: PropTypes.func,
  handleSaveOk: PropTypes.func,
  groupModalStatus: PropTypes.object,


}

export default Form.create()(DetailModal)

