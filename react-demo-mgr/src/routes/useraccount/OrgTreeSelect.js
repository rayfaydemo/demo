/**
 * Created by STZHANG on 2017/5/25.
 */
import React, { PropTypes } from 'react'
import { TreeSelect, Tree } from 'antd';
const TreeNode = Tree.TreeNode;
class OrgTreeSelect extends React.Component {

  constructor (props) {
    super(props)
    this.state = {
      treeData: [],
      initTreeData: props.initTreeData,
      autoExpandParent: true,
    }
  }

  componentDidMount () {
    this.setState({
      treeData: this.state.initTreeData ,
    });
  }

  componentWillReceiveProps(nextProps){
    if(this.state.initTreeData.length == 0) {
      this.setState({value: nextProps.value, treeData: nextProps.initTreeData, initTreeData: nextProps.initTreeData});
    }else {
      this.setState({value: nextProps.value});
    }
  }

  onChange = (value) => {
    this.setState({ value });
    this.props.handleOnChange(value);
  }
  onSelect = (info) => {
    console.log('selected', info);
  }
  onLoadData = (treeNode) => {

    console.log(treeNode)
    const treeData = [...this.state.treeData];
    const treeKey = treeNode.props.value;
    const childTreeNodesPromise = this.props.retrieveFunction(treeKey);
    let self = this;
    return childTreeNodesPromise.then(function (childTreeNodes) {
      const loopRetrieve = (data) =>{
        data.forEach(function (item) {
          if(item.orgCode == treeKey && childTreeNodes != null && childTreeNodes.length > 0){
            item.children = childTreeNodes;
          }else if(item.orgCode == treeKey){
            console.log(item.orgName + " isLeaf")
            item.children = null;
            item.isLeaf = true;
          }else if(item.children){
            loopRetrieve(item.children);
          }
        });
      }
      loopRetrieve(treeData);
      console.log(treeData)
      self.setState({ treeData });
    });
  }

  render() {
    console.log(" start to render tree ")
    const loop = data => data.map((item) => {
      if (item.children) {
        return <TreeNode value={item.orgCode} title={item.orgName} key={item.orgCode}>{loop(item.children)}</TreeNode>;
      }
      return <TreeNode value={item.orgCode} title={item.orgName} key={item.orgCode} isLeaf={item.isLeaf ? true: false} />;
    });
    const treeNodes = loop(this.state.treeData);
    return (
      <TreeSelect
        style={{ width: 240}}
        allowClear
        showCheckedStrategy="SHOW_PAREN"
        placeholder="请选择用户所属单位"
        onSelect={this.onSelect}
        loadData={this.onLoadData}
        onChange={this.onChange}
        value={this.state.value}
      >
        {treeNodes}
      </TreeSelect>
    );
  }
}

OrgTreeSelect.propTypes = {
  retrieveFunction: PropTypes.func,
  handleOnChange: PropTypes.func,
  initTreeData: PropTypes.array,
  value: PropTypes.object,
}
export default OrgTreeSelect;
