/**
 * Created by STZHANG on 2017/5/15.
 */
import PropTypes from 'prop-types';
import { Input, InputNumber  } from 'antd';
class EditableFormItem extends React.Component {
  state = {
    value: this.props.value || '',
    editable: this.props.editable || false,
    isFieldNumber: this.props.isFieldNumber || false,
  }
  componentWillReceiveProps(nextProps) {
    // 编辑状态发生了变化
    if (nextProps.editable !== this.state.editable) {
      this.setState({ editable: nextProps.editable });
      if (nextProps.editable) {
        this.cacheValue = this.state.value;
      }
    } else if (nextProps.value != this.props.value){
      this.setState({ value: nextProps.value });
    }
    // 状态发生了变化
    if (nextProps.status && nextProps.status !== this.props.status) {
      if (nextProps.status === 'save') {
        this.props.onChange(this.state.value, nextProps.status);
      } else if (nextProps.status === 'cancel') {
        this.setState({ value: this.cacheValue });
        this.props.onChange(this.cacheValue, nextProps.status);
      }
    }
  }
  shouldComponentUpdate(nextProps, nextState) {
    return nextProps.editable !== this.state.editable ||
      nextState.value !== this.state.value;
  }
  handleChange(e) {
    const value = e.target.value;
    this.setState({ value });
  }
  handleNumberChange(e) {
    this.setState({ value: e });
  }

  render() {
    const { value, editable, isFieldNumber } = this.state;
    return (
      <div>
        {
          editable ?
            <div>
              { isFieldNumber ?
                <InputNumber
                  min={1} max={100} value={value}
                  onChange={e => this.handleNumberChange(e)}
                />
                :
                <Input
                  value={value}
                  onChange={e => this.handleChange(e)}
                />
              }
            </div>
            :
            <div className="editable-row-text">
              {value.toString() || ' '}
            </div>
        }
      </div>
    );
  }
}

EditableFormItem.propTypes = {
  editable: PropTypes.bool,
  isFieldNumber: PropTypes.bool,
  onChange: PropTypes.func,
  status: PropTypes.string,
}

export default EditableFormItem
