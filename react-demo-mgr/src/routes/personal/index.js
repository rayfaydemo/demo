/**
 * Created by STZHANG on 2017/5/13.
 */
import { Form, Input } from 'antd'
import { connect } from 'dva'
const FormItem = Form.Item
const PersonalForm = (personal) => {
  const { userInfo } = personal
  const formItemLayout = {
    labelCol: { span: 6 },
    wrapperCol: { span: 14},
  };
  return (
    <Form>
      <FormItem label="账户" {...formItemLayout} key="userId">
          <Input defaultValue={userInfo.userId} />
      </FormItem>
      <FormItem label="账户" {...formItemLayout} key="userName">
          <Input defaultValue={userInfo.userName} />
      </FormItem>
    </Form>
  )
}

const mapStateToProps = function ({ personal }) {
  return personal
}

export default connect(mapStateToProps)(PersonalForm)
