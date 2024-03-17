## 中小企业供应链金融系统
### 1.研究背景
本论文在供应链金融背景下，为中小型企业设计的金融管理系统，通过提供更好的供应链金融服务，可以帮助它们克服融资难题，促进其发展和壮大。通过研究中小企业供应链金融系统，可以挖掘金融创新的潜力，提供更多种类和形式的金融工具，满足中小企业多样化的融资需求。

### 2.环境配置

#### 2.1 MySQL
建表语句 /src/main/resource/ddl.sql
自行修改yaml文件配置
#### 2.2 Redis
自行修改yaml文件配置
#### 2.3 Aliyun短信服务
申请后自行修改yaml文件配置
#### 2.4 hyperledger-fabric
智能合约代码位于src/main/resource/chaincode-go
参考：
- https://hyperledger-fabric.readthedocs.io/zh-cn/latest/test_network.html
- https://blog.csdn.net/weixin_44750512/article/details/108578755

### 3.启动项目
启动后，访问http://localhost:8083/front/page/login/emp_login.html
