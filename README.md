# JHosts
> 功能类似于Chrome的host插件，只是我用Ubuntu系统时会引起权限问题，而且有时候感觉和Chrome绑在一起不利于其他浏览器的使用，索性就写了这个程序。
项目地址：https://github.com/b3log/JHosts

项目利用了一个非常漂亮的JavaFX扩展——[JFoenix](https://github.com/jfoenixadmin/JFoenix)
其有相当丰富的组件演示。我为了省事儿直接用了Demo的主界面。

目前已经基本能够使用，只不过还欠缺更精细的完善，比如保存失败的提示，对于Host中的纯注释的提示（目前的实现逻辑将只获取IP和Domain并重新生成一个列表进行写文件，这意味着纯注释会丢失，所以注释很重要的话要备份原文件）

文件路径目前是写进了代码里，理想的情况应该是有个配置文件

增加和删除功能并没有实现，目前只有改写

原计划左侧列表有个类别（现在也能看到），分别代表所有，生产环境，测试环境，预发环境，本地环境，这是为了更直观的查看host文件，对host进行一个一级分组，暂时没有实现（因为通过搜索就可以快速定位了，这个需求也就成了锦上添花之用）

实际使用的时候仍然避不开权限，Ubuntu下（这是我主要的使用场景），写一个脚本带sudo执行即可，mac和win没有试验。

代码的结构还是有点混乱（尽可能的分离了不同功能的类），希望将来能从spring获得启发并加以实践。同时，目前代码仍然保留了Demo的全部源码（可以直接运行MainDemo查看JFoenix的全部示例），这是为了开发的时候查看方便，将来会把与本应用无关的代码移除，并作为分支存在（主干将保留Demo）

总之，这是个初步成品，日后慢慢改进吧 :joy: 

### 主界面：
![8a674827c5ee492b83e98ab995a6c2f2-image.png](https://img.hacpai.com/file/2017/9/8a674827c5ee492b83e98ab995a6c2f2-image.png) 

### 查询IP或domain：
![e637de85812540869ec2afe2fb8ed18e-image.png](https://img.hacpai.com/file/2017/9/e637de85812540869ec2afe2fb8ed18e-image.png) 

### 保存
![3a711f23064745878fea2d9a7676a544-image.png](https://img.hacpai.com/file/2017/9/3a711f23064745878fea2d9a7676a544-image.png) 

PS. 从图上能看出一个bug，有没有人能发现，发现者奖励100积分，先到先得呀


