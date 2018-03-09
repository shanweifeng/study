### github 从2018.02.01开始不再支持TLSv1.1方式clone工程文件，git版本也需要升级。当前版本git version 2.16.2.windows.1
git config http.sslVersion 查看TLSv 当前版本tlsv1.2
如果查询不是当前版本  需要将低级版本升级到tlsv1.2 git config --global http.sslVersion tlsv1.2
如果还不能clone工程 则需要查看具体原因