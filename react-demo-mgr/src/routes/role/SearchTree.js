import React, { PropTypes } from 'react'
import { Tree, Input, Button, Icon, Tag ,Row,Col} from 'antd'
import classnames from 'classnames'
import styles from './SearchTree.less'
const TreeNode = Tree.TreeNode
const Search = Input.Search
let dataList = []
const generateList = (data) => {
  for (let i = 0; i < data.length; i++) {
    const node = data[i]
    const key = node.key
    const title = node.title
    dataList.push({ key, title })
    if (node.children) {
      generateList(node.children, node.key)
    }
  }
}

const getParentKey = (key, tree) => {
  let parentKey
  for (let i = 0; i < tree.length; i++) {
    const node = tree[i]
    if (node.children) {
      if (node.children.some(item => item.key === key)) {
        parentKey = node.key
      } else if (getParentKey(key, node.children)) {
        parentKey = getParentKey(key, node.children)
      }
    }
  }
  return parentKey
}

class SearchTree extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      expandedKeys: [],
      searchValue: '',
      autoExpandParent: false,
      defaultExpandAll: true,
      loading: false,
      dataMenus: [],
      dataList: [],
    }
  }
  onExpand = (expandedKeys) => {
    this.setState({
      expandedKeys,
      autoExpandParent: false,
    })
  }

  onChange = (e) => {
    const value = e.target.value
    const { dataSource } = this.props
    const dataMenus = dataSource
    const expandedKeys = dataList.map((item) => {
      if (item.title.indexOf(value) > -1) {
        return getParentKey(item.key, dataMenus)
      }
      return null
    }).filter((item, i, self) => item && self.indexOf(item) === i)
    this.setState({
      expandedKeys,
      searchValue: value,
      autoExpandParent: true,
    })
  }



  render () {
    const { searchValue, expandedKeys, autoExpandParent,defaultExpandAll } = this.state
    const { dataSource } = this.props
    const { handleRefresh } = this.props
    const dataMenus = dataSource
    generateList(dataMenus)

    const loop = data => data.map((item) => {
      const index = item.title.search(searchValue)
      const beforeStr = item.title.substr(0, index)
      const afterStr = item.title.substr(index + searchValue.length)
      const title = index > -1 ? (
        <span>
          {beforeStr}
          <span style={{ color: '#ffaa0a' }}>{searchValue}</span>
          {afterStr}
        </span>
      ) : <span>{item.title}</span>
      if (item.children) {
        return (
          <TreeNode key={item.key} title={title}>
            {loop(item.children)}
          </TreeNode>
        )
      }
      return <TreeNode key={item.key} title={title} />
    })
    return (
      <div className={classnames(styles.borderdiv)} >
        <div>
          <Row>
            <Col span={11}>
              <Search style={{ width: "100%" }} placeholder="Search" onChange={this.onChange} />
            </Col>
            <Col span={13}>
              <Icon style={{ paddingLeft: 8, paddingRight: 8,cursor: 'pointer' }} onClick={handleRefresh} type="retweet" />
              <Button style={{  }}  onClick={this.props.saveHandel}>保存</Button>
            </Col>
          </Row>

        </div>
        <Tree
          checkable
          showLine
          showIcon
          defaultExpandAll={defaultExpandAll}
          onExpand={this.onExpand}
          expandedKeys={expandedKeys}
          autoExpandParent={autoExpandParent}
          onSelect={this.props.handleNodeClick}
          onCheck={this.props.handleNodeCheck}
          checkedKeys={this.props.selectTreeNode}
        >
          {loop(dataMenus)}
        </Tree>
      </div>
    )
  }
}

export default SearchTree
