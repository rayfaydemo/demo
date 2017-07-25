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
                  retrieveDepart,
                  loading,
                  onFilterChange,
                  initTreeData,
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

  console.log('initTreeData')
  console.log(initTreeData)

  const orgTreeSelectProps = {
    retrieveFunction: retrieveDepart,
    initTreeData, //  [{"orgCode":"01", "orgName":"P国家铁路局"}],
    handleOnChange: function (val) {
      console.log(val)
      setFieldsValue({"departCode": val})
    }
  }

  return (
    <Row gutter={10}>
      <Col {...ColProps} xl={{ span: 4 }} md={{ span: 4 }}>
        {getFieldDecorator('userCode', { initialValue: "" })(<Input placeholder="用户编号" />)
        }
      </Col>
      <Col {...ColProps} xl={{ span: 4 }} md={{ span: 4 }}>
        {getFieldDecorator('userText', { initialValue: "" })(<Input placeholder="用户姓名" />)
        }
      </Col>
      <Col {...ColProps} xl={{ span: 9 }} md={{ span: 9 }}>
        {getFieldDecorator('departCode', { initialValue: "" })(<OrgTreeSelect value='' {...orgTreeSelectProps} />
        )}
      </Col>
      <Col xl={{ span: 7 }} md={{ span: 7 }}  >
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <div >
            <Button type="primary" icon="search" size="large" className="margin-right" onClick={handleRetrieve} loading={loading}></Button>
            <Button size="large" icon="rollback"  onClick={handleReset}></Button>
          </div>
        </div>
      </Col>
    </Row>
  )
}

Filter.propTypes = {
  loading: PropTypes.bool,
  filter: PropTypes.object,
  form: PropTypes.object,
  initTreeData: PropTypes.array,
  onFilterChange: PropTypes.func,
  retrieveDepart: PropTypes.func,
}

export default Form.create()(Filter)
