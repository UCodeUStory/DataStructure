### 组合模式 


1. 将对象组合成树形结构以表示“部分-整体”的层次结构，使得用户对单个对象和组合对象的使用具有一致性。

2.  介绍

    组合模式属于结构型模式。
    
    组合模式有时叫做部分—整体模式，主要是描述部分与整体的关系。
    
    组合模式实际上就是个树形结构，一棵树的节点如果没有分支，就是叶子节点;如果存在分支，则是树枝节点。
    
    我们平时遇到的最典型的组合结构就是文件和文件夹了，具体的文件就是叶子节点，而文件夹下还可以存在文件和文件夹，所以文件夹一般是树枝节点
    
    
    
3. 例子



      透明的组合模式
      public class Content extends PageElement {//具体内容

        public Content(String name) {
            super(name);
        }

        @Override
        public void addPageElement(PageElement pageElement) {
            throw new UnsupportedOperationException("不支持此操作");
        }

        @Override
        public void rmPageElement(PageElement pageElement) {
            throw new UnsupportedOperationException("不支持此操作");
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("不支持此操作");
        }

        @Override
        public void print(String placeholder) {
            System.out.println(placeholder + "──" + getName());
        }
        
      }
      
      public class Column extends PageElement {//栏目
      
              public Column(String name) {
                  super(name);
              }
      
              @Override
              public void addPageElement(PageElement pageElement) {
                  mPageElements.add(pageElement);
              }
      
              @Override
              public void rmPageElement(PageElement pageElement) {
                  mPageElements.remove(pageElement);
              }
      
              @Override
              public void clear() {
                  mPageElements.clear();
              }
              
              /**
               * @param placeholder 占位符
               */
              @Override
              public void print(String placeholder) {
                  //利用递归来打印文件夹结构
                  System.out.println(placeholder + "└──" + getName());
                  Iterator<PageElement> i = mPageElements.iterator();
                  while (i.hasNext()) {
                      PageElement pageElement = i.next();
                      pageElement.print(placeholder + "   ");
                  }
              }
      
          }
      
      
      public void test() {
              //创建网站根页面 root
              PageElement root = new Column("网站页面");
              //网站页面添加两个栏目：音乐,视屏;以及一个广告内容。
              PageElement music = new Column("音乐");
              PageElement video = new Column("视屏");
              PageElement ad = new Content("广告");
              root.addPageElement(music);
              root.addPageElement(video);
              root.addPageElement(ad);
      
              //音乐栏目添加两个子栏目：国语,粤语
              PageElement chineseMusic = new Column("国语");
              PageElement cantoneseMusic = new Column("粤语");
              music.addPageElement(chineseMusic);
              music.addPageElement(cantoneseMusic);
      
              //国语,粤语栏目添加具体内容
              chineseMusic.addPageElement(new Content("十年.mp3"));
              cantoneseMusic.addPageElement(new Content("明年今日.mp3"));
      
              //视频栏目添加具体内容
              video.addPageElement(new Content("唐伯虎点秋香.avi"));
      
              //打印整个页面的内容
              root.print("");
          }
          
          


    安全的组合模式
    
    
    public abstract class PageElement {//页面
            private String name;

        public PageElement(String name) {
            this.name = name;
        }

        //抽象组件角色去掉增删等接口

        public abstract void print(String placeholder);

        public String getName() {
            return name;
        }
    }
    
    public class Content extends PageElement {//具体内容，只专注自己的职责

        public Content(String name) {
            super(name);
        }
        
        @Override
        public void print(String placeholder) {
            System.out.println(placeholder + "──" + getName());
        }
    }
    
    public class Column extends PageElement {//栏目
        private List<PageElement> mPageElements = new ArrayList<>();//用来保存页面元素

        public Column(String name) {
            super(name);
        }

        public void addPageElement(PageElement pageElement) {
            mPageElements.add(pageElement);
        }

        public void rmPageElement(PageElement pageElement) {
            mPageElements.remove(pageElement);
        }

        public void clear() {
            mPageElements.clear();
        }

        @Override
        public void print(String placeholder) {
            System.out.println(placeholder + "└──" + getName());
            Iterator<PageElement> i = mPageElements.iterator();
            while (i.hasNext()) {
                PageElement pageElement = i.next();
                pageElement.print(placeholder + "   ");
            }
        }

    }
    
    public void test() {//客户端测试方法
        //依赖具体的实现类Column
        Column root = new Column("网站页面");
       
        Column music = new Column("音乐");
        Column video = new Column("视屏");
        PageElement ad = new Content("广告");
        root.addPageElement(music);
        root.addPageElement(video);
        root.addPageElement(ad);

        Column chineseMusic = new Column("国语");
        Column cantoneseMusic = new Column("粤语");
        music.addPageElement(chineseMusic);
        music.addPageElement(cantoneseMusic);

        chineseMusic.addPageElement(new Content("十年.mp3"));
        cantoneseMusic.addPageElement(new Content("明年今日.mp3"));

        video.addPageElement(new Content("唐伯虎点秋香.avi"));

        root.print("");
    }


安全的组合模式将职责区分开来放在不同的接口中，这样一来，设计上就比较安全，也遵循了单一职责原则和接口隔离原则，但是也让客户端必须依赖于具体的实现；透明的组合模式，以违反单一职责原则和接口隔离原则来换取透明性，但遵循依赖倒置原则，客户端可以直接依赖于抽象组件即可，将叶子和树枝一视同仁，也就是说，一个元素究竟是枝干节点还是叶子节点，对客户端是透明的。
　　一方面，我们写代码时应该遵循各种设计原则，但实际上，有些设计模式原则在使用时会发生冲突，这就需要我们根据实际情况去衡量做出取舍，适合自己的才是最好的。
