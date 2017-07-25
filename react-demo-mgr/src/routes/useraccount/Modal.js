import React from 'react'
import PropTypes from 'prop-types'
import { Form, Button, InputNumber, Radio, Modal, Cascader } from 'antd'
import city from '../../utils/city'

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
  ...modalProps
}) => {
  const modalOpts = {
    ...modalProps,
    onCancel: handleCancel,
    footer: [<Button key="back" size="large" onClick={handleCancel}>关闭</Button>],
  }
  return (
    <Modal {...modalOpts}>
      <Form layout="horizontal">
        <FormItem label="账户编号" hasFeedback {...formItemLayout}>
          {item.userId}
        </FormItem>
        <FormItem label="姓  名" hasFeedback {...formItemLayout}>
          {item.userName}
        </FormItem>
        <FormItem label="性  别" hasFeedback {...formItemLayout}>
          {item.genderName}
        </FormItem>
        <FormItem label="身份类型" hasFeedback {...formItemLayout}>
          {item.accountType}
        </FormItem>
        <FormItem label="部门代码" hasFeedback {...formItemLayout}>
          {item.departCode}
        </FormItem>

        <FormItem label="部门名称" hasFeedback {...formItemLayout}>
          {item.departName}
        </FormItem>

        <FormItem label="身份证号" hasFeedback {...formItemLayout}>
          {item.societyId}
        </FormItem>

        <FormItem label="状  态" hasFeedback {...formItemLayout}>
          {item.enabled ? "正常": "停用"}
        </FormItem>

      </Form>
    </Modal>
  )
}

DetailModal.propTypes = {
  item: PropTypes.object,
  handleCancel: PropTypes.func,
}

export default Form.create()(DetailModal)
