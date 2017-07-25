/**
 * Created by STZHANG on 2017/6/22.
 */
/**
 * Created by shenfu on 2017/5/17.
 */
import React, { PropTypes } from 'react'
import { Form, Button, Row, Col, Input, Select } from 'antd'
import  OrgTreeSelect from '../useraccount/OrgTreeSelect'
const FormItem = Form.Item;
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
                  loading,
                  onFilterChange,
                  initGroupTypes,
                  form: {
                    getFieldDecorator,
                    getFieldsValue,
                    setFieldsValue,
                  },
                }) => {


  const handleRetrieve = () => {
    let fields = getFieldsValue();
    console.log(fields);
    onFilterChange(fields);
  }

  const handleReset = () => {
    const fields = getFieldsValue();
    for (let item in fields) {
      if ({}.hasOwnProperty.call(fields, item)) {
        if (fields[item] instanceof Array) {
          fields[item] = []
        } else {
          fields[item] = undefined
        }
      }
    }
    console.log(fields)
    setFieldsValue(fields)
  }

  return (
    <Row gutter={24}>
      <Col {...ColProps} xl={{ span: 8 }} md={{ span: 8 }}>
        {getFieldDecorator('groupName', { initialValue: "" })(<Input placeholder="用户组名称" />)
        }
      </Col>

      <Col {...ColProps} xl={{ span: 8 }} md={{ span: 8 }}>
        {getFieldDecorator('groupType', { initialValue: "" })(
          <Select defaultValue="" style={{ width: 120 }} allowClear placeholder="用户组类型">
            {
              initGroupTypes.map(function(item){
                return (<Option value={item.typeId}>{item.typeName}</Option>)
              })
            }
          </Select>
        )}
      </Col>
      <Col xl={{ span: 8 }} md={{ span: 8 }} sm={{ span: 24 }}>
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <div >
            <Button type="primary" icon="search" size="large" className="margin-right" onClick={handleRetrieve} loading={loading}>查询</Button>
            <Button size="large" icon="rollback"  onClick={handleReset}>重置</Button>
          </div>
        </div>
      </Col>
    </Row>
  )
}

Filter.propTypes = {
  loading: PropTypes.bool,
  form: PropTypes.object,
  initGroupTypes: PropTypes.array,
  onFilterChange: PropTypes.func,
}

export default Form.create()(Filter)
