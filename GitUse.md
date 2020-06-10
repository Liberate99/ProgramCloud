## git使用方法说明(命令行版)

- 首先从远程仓库导入项目

> git clone  仓库地址

- 首先选择分支

> git checkout 分支名称

- 查看当前分支状态

>git status

- 缓存改动

> git add 改动文件

- 提交到本地仓库

>git commit -m "评论说明"

- 从远程拉取别人的改动

> git pull

- 提交本地改动到远程仓库

> git push

## 合并主分支代码操作

*注意合并之前确认自己分支所有改动已经提交*

- 首先切换到主分支

> git checkout master

- 更新拉取代码

> git pull

- 切换到自己分支

> git checkout kkk

- 执行合并代码

> git merge master

- 如果有冲突需要解决冲突，相当于重新做了改动，需要再次提交