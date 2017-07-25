/**
 * Created by STZHANG on 2017/6/26.
 */
/**
 * Created by STZHANG on 2017/6/22.
 */

import React from 'react'
import PropTypes from 'prop-types'
import { Form, Modal, Checkbox } from 'antd'
const CheckboxGroup = Checkbox.Group;
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
                       userRoles,
                       ...modalProps,
                       form: {
                         getFieldDecorator,
                         getFieldsValue,
                         getFieldValue,
                         validateFields,
                         resetFields,
                       },
                     }) => {


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

  const modalOpts = {
    ...modalProps,
    onCancel: onCancel,
    onOk: onOK,
  }

  const retrieveUserRoles = () =>{
     let privileges = [];
     if(userRoles){
       userRoles.map(iem => {
         privileges.push({label: iem.roleName, value: iem.roleId});
       })
     }
     return privileges;
  }

  function onChange(checkedValues) {
    console.log('checked = ', checkedValues);
  }

  return (
    <Modal {...modalOpts}>
      <Form >
        <FormItem {...formItemLayout} label="用户组权限">
          {getFieldDecorator('rolePrivileges', {
            rules: [{
              required: false,
            }],
            initialValue: item.privileges,
          })(<CheckboxGroup options={retrieveUserRoles()}  onChange={onChange} ></CheckboxGroup>)
         }
        </FormItem>
      </Form>
    </Modal>
  )
}

DetailModal.propTypes = {
  item: PropTypes.object,
  userRoles: PropTypes.array,
  handleCancel: PropTypes.func,
  handleSaveOk: PropTypes.func,
}

export default Form.create()(DetailModal)

