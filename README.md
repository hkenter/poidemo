# poidemo
word文档段落样式分析与校验

实现思路：
Office2003以上，Word可以以XML文本格式存储，利用apache poi解析文档中xml相关属性，解析段落层级与内容。
