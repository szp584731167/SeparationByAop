[参考文章](https://mp.weixin.qq.com/s?__biz=MzAxNjk4ODE4OQ==&mid=2247489384&idx=1&sn=71388a35ea15fa4367b387b5d280fda9&chksm=9bed361aac9abf0c832cf19ba5c6eaa5e9ba10ae867ee053094920d584afac95bf574db175f6&mpshare=1&scene=1&srcid=0605e9XY0ZUB3fZkQ8WE6xYU&sharer_sharetime=1591337447204&sharer_shareid=8bc0fc0eec5b1ea08779a3dede3a2474&key=bf1c932dfaac674c4e0b5a76930ddb348d79da782b87c2e5640ad732af978a5fe21085c759678f7801786f69519d76812bbc9bae83239240505b2799be497cdeb9adeada0ad2b6d12a87a79d9d3c91b3&ascene=1&uin=MTg1MzM1NDgzMw%3D%3D&devicetype=Windows+10+x64&version=62090070&lang=zh_CN&exportkey=A%2FJV945V69Mws0nzMmSMsiY%3D&pass_ticket=5kYo41Y2tPyRcyTO8Zwuv8q7odda4AjIe7dRN2HQjUbQfseYesZKJoPE6phyDHgL)


[主从复制环境搭建](https://blog.csdn.net/qq_42726668/article/details/106818456)

本文关键就在于自己创建的注解，并以该注解为切点，使用环绕型切入

1. 进入方法时根据注解进入AOP方法
2. 根据注解所获存取的参数判断需要连接哪个数据库，并保至TreadLocal，保证了数据安全
3. 在mapper执行的时候需要获取连接，那么根据保存的数据库类型，获取类型进行判断路由。最终选取到我们所需的数据库上面
4. 释放TreadLocal以免内存溢出


那如果我们数据库中急需要读有需要写的时候应该如何做呢？
可以将业务进行分割，然后使用不同的方法，最终封装达到业务需求



解释建议去看一下参考文章哟。 

若侵权，联系可删


添加观察者模式：event包

观察者模式与监听器模式的区别：

观察者模式，在一个主题下有一个观察者，当发现事件发布后执行相应的监听。

监听器模式，有一个事件源还有一个事件，这两个加一起的时候相当于一个观察者，弄不清，草