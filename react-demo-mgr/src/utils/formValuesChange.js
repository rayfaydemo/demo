/**
 * Created by shenfu on 2017/6/16.
 */

const onValuesChange = (props, values) => {
  const { dispatch } = props
  dispatch({
    type: 'app/hasUnsavedChange',
    payload: { hasUnsavedChange: true },
  })
}

const formCreateOption = {
  onValuesChange,
}

export default formCreateOption
