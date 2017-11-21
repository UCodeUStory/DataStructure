var Node = function (_date,_parent) {
    this.Data = _date;
    this.Parent = _parent;
    this.LeftNode = null;
    this.RightNode = null;
}

var BinaryTree = function () {
    this.Root = null;//根节点

    this.Insert = function (insertValue) {
        if (this.Root == null) {
            this.Root = new Node(insertValue,null);
            return;
        }

        var node = this.Root;
        while (true) {
            if (node.Data > insertValue) {
                if (node.LeftNode == null) {
                    node.LeftNode = new Node(insertValue,node);
                    break;
                } else {
                    node = node.LeftNode;
                }
            } else if (node.Data < insertValue) {
                if (node.RightNode == null) {
                    node.RightNode = new Node(insertValue, node);
                    break;
                } else {
                    node = node.RightNode;
                }
            } else {
                break;
            }
        }
    };

    var maxLevel;
    var level;
    this.Level = function () {
        maxLevel = 0;
        level = 0;
        return levels(this.Root);
    }

    function levels(node) {
        if (node.LeftNode != null) {
            level++;
            levels(node.LeftNode);
        }
        maxLevel = Math.max(maxLevel, level);

        if (node.RightNode != null) {
            level++;
            levels(node.RightNode);
        }
        level--;
        return maxLevel;
    }

    this.SetPoint = function () {
        var thisMaxLevel = this.Level();
        var childQuanty = Math.pow(2, thisMaxLevel);

        this.Root.nodeLevel = 0;
        this.Root.nodePoint = 0;

        if (this.Root.LeftNode != null)
        {
            setPointsLeft(this.Root.LeftNode, -1 * childQuanty / 2, 0, thisMaxLevel - 1);
        }
        
        if (this.Root.RightNode != null)
        {
            setPointsRight(this.Root.RightNode, childQuanty / 2, 0, thisMaxLevel - 1);
        }
    }

    function setPointsLeft(node, point, levels, thisMaxLevel) {
        ++levels;
        node.nodeLevel = levels;
        node.nodePoint = point;
 
        if (node.LeftNode != null) {
            setPointsLeft(node.LeftNode, point - Math.pow(2, thisMaxLevel - levels), levels, thisMaxLevel);
        }

        if (node.RightNode != null) {
            setPointsLeft(node.RightNode, point + Math.pow(2, thisMaxLevel - levels), levels, thisMaxLevel);
        }
    }

    function setPointsRight(node, point, levels, thisMaxLevel) {
        ++levels;
        node.nodeLevel = levels;
        node.nodePoint = point;

        if (node.LeftNode != null) {
            setPointsRight(node.LeftNode, point - Math.pow(2, thisMaxLevel - levels), levels, thisMaxLevel);
        }

        if (node.RightNode != null) {
            setPointsRight(node.RightNode, point + Math.pow(2, thisMaxLevel - levels), levels, thisMaxLevel);
        }
    }

    this.PreOrder = function (funs) {
        preOrder(this.Root, funs);
    }

    function preOrder(node, funs) {
        funs(node);

        if (node.LeftNode != null) {
            preOrder(node.LeftNode, funs);
        }

        if (node.RightNode != null) {
            preOrder(node.RightNode, funs);
        }
    }

    this.Search = function (number)
    {
        return search(this.Root, number);;
    }

    function search(node,number)
    {
        if (node == null)
        {
            return null;
        }

        if (node.Data>number)
        {
            return search(node.LeftNode,number);
        } else if (node.Data < number) {
            return search(node.RightNode, number);
        } else {
            return node;
        }
    }

    this.Remove = function (number)
    {
        var node = this.Search(number);
        if (node != null)
        {
            remove.call(this,node);
        }
    }

    function remove(node)
    {
        var child, parent;
        if (node.LeftNode != null && node.RightNode != null) {
            var tempNode = findMin(node.RightNode);

            if (node.Parent == null) {
                this.Root = tempNode;
            } else {
                if (node.Parent.LeftNode == node) {
                    node.Parent.LeftNode = tempNode;
                } else {
                    node.Parent.RightNode = tempNode;
                }
            }

            child = tempNode.RightNode;
            parent = tempNode.Parent;

            if (parent.Data == node.Data) {
                parent = tempNode;
            } else {
                if (child != null) {
                    child.Parent = parent;
                }
                parent.LeftNode = child;

                tempNode.RightNode = node.RightNode;
                node.RightNode.Parent = tempNode;
            }

            tempNode.Parent = node.Parent;
            tempNode.LeftNode = node.LeftNode;
            node.LeftNode.Parent = tempNode;
        } else {

            if (node.LeftNode != null) {
                child = node.LeftNode;
            } else {
                child = node.RightNode;
            }

            parent = node.Parent;

            if (child != null) {
                child.Parent = parent;
            }

            if (parent != null) {
                if (parent.LeftNode!=null && parent.LeftNode.Data == node.Data) {
                    parent.LeftNode = child;
                } else {
                    parent.RightNode = child;
                }
            } else {
                this.Root = child;
            }
        }
        node = null;
    }

    this.FindMin = function ()
    {
        findMin(this.Root);
    }

    function findMin(node)
    {
        if (node.LeftNode == null)
        {
            return node;
        }
        return findMin(node.LeftNode);
    }

    this.FindMax = function ()
    {
        return findMax(this.Root);
    }

    function findMax(node)
    {
        if (node.RightNode == null)
        {
            return node;
        }
        return findMax(node.RightNode);
    }
}