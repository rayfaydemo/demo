/**
 * Created by shenfu on 2017/5/17.
 */
import React, { PropTypes } from 'react'
import { Form, Input, Tooltip, Icon, Cascader, Modal, Select, Row, Col, Checkbox, Button, message, InputNumber } from 'antd'
const FormItem = Form.Item
const ColProps = {
  xs: 24,
  sm: 12,
  style: {
    marginBottom: 16,
  },
}

const DetailForm = ({
  roleTypeList,
  visible,
  dispatch,
  searchData,
  pagination,
  formData,
  editDisabled,
  form: {
    getFieldDecorator,
    getFieldsValue,
    setFieldsValue,
    validateFields,
    resetFields,
  },
}) => {
  const onCancel = () => {
    dispatch({
      type: 'roleList/closeModal',
      payload: {
        resetFields,
      },
    })
  }

  const onCreate = () => {
    let fields = getFieldsValue()
    validateFields((errors) => {
      if (errors) {
        return
      }
      dispatch({
        type: 'roleList/saveRole',
        payload: {
          data: fields,
          queryData: searchData.queryData,
          currentPage: pagination.current,
          pageSize: pagination.pageSize,
          sorter: searchData.sorter,
          edit: editDisabled,
          resetFields,
        },
      })
    });

  }


  const formItemLayout = {// 指定布局
    labelCol: {
      xs: { span: 24 },
      sm: { span: 6 },
    },
    wrapperCol: {
      xs: { span: 24 },
      sm: { span: 12 },
    },
  }
  return (
    <Modal
      visible={visible}
      title="新增角色"
      okText="保存"
      cancelText="关闭"
      maskClosable={false}
      onCancel={onCancel}
      onOk={onCreate}
    >
      <Form >
        <FormItem {...formItemLayout} label="角色标识" hasFeedback>
          {getFieldDecorator('roleId', {
            rules: [{
              required: true, message: '请输入角色标识!',
            }],
            initialValue: formData.roleId,
          })(<Input readOnly={editDisabled} />)}

        </FormItem>
        <FormItem {...formItemLayout} label="角色类型" hasFeedback>
          {getFieldDecorator('roleType', {
            rules: [{
              required: true, message: '请选择角色类型!',
            }],
            initialValue: formData.roleType,
          })(<Select placeholder="请选择身份类型">
            {roleTypeList.map(option => <Option key={option.value} value={option.value}>{option.text}</Option>)}
          </Select>)}

        </FormItem>

        <FormItem {...formItemLayout} label="角色名称" hasFeedback>
          {getFieldDecorator('roleName', {
            rules: [{
              required: true, message: '请输入角色名称!',
            }],
            initialValue: formData.roleName,
          })(<Input />)}

        </FormItem>


      </Form>
    </Modal>
  )
}


DetailForm.propTypes = {
  roleTypeList: PropTypes.array,
  visible: PropTypes.bool,
  dispatch: PropTypes.func,
  form: PropTypes.object,
  searchData: PropTypes.object,
  pagination: PropTypes.object,
  formData: PropTypes.object,
  editDisabled: PropTypes.bool,
}

export default Form.create()(DetailForm)
