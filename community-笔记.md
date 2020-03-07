# Conmmunity 项目
## 一、环境配置
1. GitHub 与 git。github 创建仓库，添加ssh key。
2. idea terminal 部署git，settings-tools-terminal-shellpath
3. git 常用操作
~~~java
// 查询所有项目
ls -a
// 进入配置文件
vim .git/config
// 配置用户名和邮箱
insert：clrl+f12
exit：clrl+c
退出：:q, 保存退出:wq
// 查看修改
git status
    
git init
git add README.md
git commit -m "first commit"
git remote add origin https://github.com/hardalec/commnunity.git
git push -u origin master
~~~

快捷操作

shift + enter，换行

alt + insert，构造器等方法



## 二、bootsttap

[boostrap 文档](https://v3.bootcss.com/components/#navbar)





## 拦截器

问题：

1. 使用拦截器后，未登录状态-即拦截状态，失去css样式。登录后恢复
2. 拦截的判定方式有两种，一种是从session中拿值，一种是从cookies中拿值。
3. request.getsession().setattribute()，需要在登录授权的时候加上一次，若不加，授权完毕跳转首页后，是不刷新首页的，因此虽然已经登录，但是“登录”两个字没有改变为用户名，因为html中要根据session 判定显示的；其次，如果首页不加request.getsession().setattribute()，那么在保持登录状态或下次登陆时，虽然能够从cookies中拿到token保持登录，但是同样的原因，登录信息没有写入session，因此”登录“也没有变成用户名。
4. **总之bug有点多，以后再来重构**



