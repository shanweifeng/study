### github 从2018.02.01开始不再支持TLSv1.1方式clone工程文件，git版本也需要升级。当前版本git version 2.16.2.windows.1
git config http.sslVersion 查看TLSv 当前版本tlsv1.2
如果查询不是当前版本  需要将低级版本升级到tlsv1.2 git config --global http.sslVersion tlsv1.2
如果还不能clone工程 则需要查看具体原因

git最新版本下载地址 https://git-scm.com/downloads
 如果下载的是安装版，则安装next之后再环境变量中将当前安装路径放入环境变量PATH中(如:D:\Program Files\Git\cmd)

 如果idea需要集成git工具则需要在工具-》version controller-》git中添加git启动路径(如:D:\Program Files\Git\cmd\git.exe)

 如果idea中需要clone gitHub上面工程，需要单独配置:工具-》version controller-》gitHub中添加https://github.com git中的登录名 登录密码 测试是否联通

 ###git替换远程仓库地址
    1、命令修改 git remote set-url origin [url]
    2、先删除 后添加 git remote rm origin
              git remote add origin [url]
    3、config中直接修改

### git 基础命令使用
    https://git-scm.com/book/zh/v2/Git-%E5%9F%BA%E7%A1%80-%E8%BF%9C%E7%A8%8B%E4%BB%93%E5%BA%93%E7%9A%84%E4%BD%BF%E7%94%A8

 ### idea中修改远程仓库地址

修改