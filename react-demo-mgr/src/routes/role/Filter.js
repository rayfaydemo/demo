/**
 * Created by shenfu on 2017/5/17.
 */
import React, { PropTypes } from 'react'
import { Form, Button, Row, Col, Input, Select } from 'antd'

const ColProps = {
  xs: 24,
  sm: 12,
  style: {
    marginBottom: 16,
  },
}

const TwoColProps = {
  ...ColProps,
  xl: 96,
}

const Filter = ({
                  roleTypeList,
                  loading,
                  pagination,
                  filter,
                  onFilterChange,
                  form: {
                    getFieldDecorator,
                    getFieldsValue,
                    setFieldsValue,
                  },
                }) => {
  const { roleId, roleType, roleText } = filter

  const handleSubmit = () => {
    let fields = getFieldsValue()
    onFilterChange(fields, pagination)
  }

  const handleReset = () => {
    const fields = getFieldsValue()
    for (let item in fields) {
      if ({}.hasOwnProperty.call(fields, item)) {
        if (fields[item] instanceof Array) {
          fields[item] = []
        } else {
          fields[item] = undefined
        }
      }
    }
    setFieldsValue(fields)
  }

  return (
    <Row gutter={16}>
      <Col {...ColProps} xl={{ span: 6 }} md={{ span: 5 }}>
        {getFieldDecorator('roleId', { initialValue: roleId })(<Input placeholder="角色标识" style={{ width: '100%' }}
          size="large"
        >
        </Input>)}
      </Col>
      <Col {...ColProps} xl={{ span: 6 }} md={{ span: 5 }}>
        {getFieldDecorator('roleType', { initialValue: roleType })(<Select placeholder="角色类型" style={{ width: '100%' }}
          size="large" allowClear
        >
          {roleTypeList.map(option => <Option key={option.value} value={option.value}>{option.text}</Option>)}
        </Select>)}
      </Col>
      <Col {...ColProps} xl={{ span: 6 }} md={{ span: 5 }}>
        {getFieldDecorator('roleText', { initialValue: roleText })(<Input placeholder="角色名称"
          style={{ width: '100%' }} size="large"
        />)}
      </Col>
      <Col {...ColProps} xl={{ span: 6 }} md={{ span: 9 }}>
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <div >
            <Button type="primary" className="margin-right" onClick={handleSubmit} loading={loading}>查询</Button>
            <Button  style={{marginLeft: 4}}  className="margin-right"  onClick={handleReset}>重置</Button>
          </div>
        </div>
      </Col>

    </Row>
  )
}

Filter.propTypes = {
  roleTypeList: PropTypes.array,
  loading: PropTypes.bool,
  pagination: PropTypes.object,
  filter: PropTypes.object,
  form: PropTypes.object,
  onFilterChange: PropTypes.func,
}

export default Form.create()(Filter)
