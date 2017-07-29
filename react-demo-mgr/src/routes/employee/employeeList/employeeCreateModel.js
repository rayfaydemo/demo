import React, { PropTypes } from 'react'
import { Form, Modal, Input, Radio } from 'antd'

const FormItem = Form.Item
const RadioGroup = Radio.Group

const formItemLayout = {
  labelCol: {
    span: 6,
  },
  wrapperCol: {
    span: 14,
  },
}

const EmployeeCreateModel = ({
                               onOk,
                               form: {
                                 getFieldDecorator,
                                 validateFields,
                                 getFieldsValue,
                               },
                               ...modalProps,
                               editObj,
                             }) => {
  const handleOk = () => {
    validateFields((errors) => {
      if (errors) {
        return
      }

      let data
      if (editObj === null) {
        data = {
          employee: {
            ...getFieldsValue(),
          },
          isCreate: true,
        }
      } else {
        data = {
          employee: {
            id: editObj.id,
            ...getFieldsValue(),
          },
          isCreate: false,
        }
      }

      onOk(data)
    })
  }

  return (
    <Modal {...modalProps} onOk={handleOk} okText="保存" title="新增职员">
      <Form layout="horizontal">
        <FormItem label="职员姓名" hasFeedback {...formItemLayout}>
          {getFieldDecorator('name', {
            initialValue: editObj !== null ? editObj.name : '',
            rules: [
              {
                required: true,
                whitespace: true,
                message: '必须输入姓名',
              },
              {
                min: 2,
                max: 30,
                message: '请输入2到30个字',
              },
            ],
          })(<Input />)}
        </FormItem>
        <FormItem label="性别" hasFeedback {...formItemLayout}>
          {getFieldDecorator('sex', {
            initialValue: editObj !== null ? editObj.sex : '',
            rules: [
              {
                required: true,
                whitespace: true,
                message: '必须指定性别',
              },
            ],
          })(
            <RadioGroup>
              <Radio value="1">男</Radio>
              <Radio value="0">女</Radio>
            </RadioGroup>
          )}
        </FormItem>
        <FormItem label="手机" hasFeedback {...formItemLayout}>
          {getFieldDecorator('telNumber', {
            initialValue: editObj !== null ? editObj.telNumber : '',
            rules: [
              {
                required: true,
                whitespace: true,
                message: '必须输入手机号码！',
              },
              {
                len: 11,
                message: '请输入11位手机号码',
              },
              {
                pattern: new RegExp('^[0-9]+$'),
                message: '只能输入数字',
              },
            ],
          })(<Input />)}
        </FormItem>
        <FormItem label="地址" hasFeedback {...formItemLayout}>
          {getFieldDecorator('address', {
            initialValue: editObj !== null ? editObj.address : '',
            rules: [
              {
                required: true,
                whitespace: true,
                message: '必须输入地址',
              },
              {
                min: 2,
                max: 30,
                message: '请输入2到30个字',
              },
            ],
          })(<Input />)}
        </FormItem>
      </Form>
    </Modal>
  )
}

EmployeeCreateModel.propTypes = {
  onOk: PropTypes.func.isRequired,
  form: PropTypes.object.isRequired,
  dispatch: PropTypes.func.isRequired,
  editObj: PropTypes.object,
}

export default Form.create()(EmployeeCreateModel)
