import React from 'react'
import ReactDOM from 'react-dom'
import enquire from 'enquire.js'
import { scrollScreen } from 'rc-scroll-anim'
import { BackTop, Affix, Row, Col } from 'antd' 
import Nav from '../../components/Nav'
import Footer from '../../components/Footer'

export default class User extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      isMode: false
    };
  }

  componentDidMount() {
    // 适配手机屏幕;
    this.enquireScreen((isMode) => {
      this.setState({ isMode });
    });
  }

  enquireScreen = (cb) => {
    /* eslint-disable no-unused-expressions */
    enquire.register('only screen and (min-width: 320px) and (max-width: 767px)', {
      match: () => {
        cb && cb(true);
      },
      unmatch: () => {
        cb && cb();
      },
    });
    /* eslint-enable no-unused-expressions */
  }

  render() {
    return (
      <div className="templates-wrapper">
        <Affix><Nav id="nav_0_0" key="nav_0_0" isMode={this.state.isMode} /></Affix>,
        <div className="web">
          {/* 标题 */}
        <div className="banner"><h1>App渗透测试</h1></div>
        <div className="Myservice">
        <div className="Myservice_All">
        <div className="Myservice_logo"><img src="/static/services/web-1.png" alt="服务概述" /></div>
        <div className="Myservice_Content">
              <h1>我们的服务</h1>
              <h2 className="xia">——————</h2>
              <p>针对客户APP安全渗透测试服务，孝道科技TCSEC团队在经客户授权许可的前提下，利用基于静态与动态相结合的检测和攻击技术，检测客户端是否有潜在的安全风险，可帮助企业全面发现业务漏洞及风险，包括静态破解、窃取源码、二次打包、本地储存数据窃取、界面截取、输入法攻击、协议抓取、SQL注入等漏洞进行测试.</p>
            </div>
        </div>  
        </div> 
        <div className="mainTest">
            <div className="mainTest_ALL">
              <div className="mainTest_content"> <h1>检测要点</h1>
                <h2 className="xia">——————</h2>
                <ul className="testContent" id="outer">
                <li>客户端检查</li><li>二进制文件弱保护</li><li>账户锁定未实现</li><li>应用程序XSS漏洞</li>
                <li>认证绕过</li><li>应用程序中敏感信息硬编码</li><li>恶意文件上传</li><li>会话固定</li>
                <li>应用程序没有验证MSISDN</li>
                <li>权限溢出</li><li>SQL注入</li><li>Debug设置为TRUE检查</li><li>绕过第二层验证</li>
                <li>LDAP注入</li><li>OS命令注入</li><li>IOS snapshot/backgrounding</li><li>弱加密算法</li>
                <li>SSL隧道明文信息检查</li>
                <li>ASLR未被使用</li><li>剪切板未禁用</li><li>敏感信息明文传输</li><li>CAPTCHA在登陆页面的设置</li>
                <li>应用日志中包含敏感信息</li><li>存储敏感信息在App沙盒外</li><li>URL修改</li><li>Dump内存中的敏感信息</li>
                <li>Autocomplete没有设置为OFF</li>
                <li>密码修改测试</li><li>Service劫持</li><li>应用程序可以在被ROOT或越狱手机中使用</li><li>使用持久性Cookies</li>
                <li>不安全的应用程序中权限设置</li><li>应用程序中包含过时的文件</li><li>私有IP泄露</li><li>通过修改RMS文件进行UI模拟</li>
                <li>没有证书绑定</li><li>卸载应用后缓存的Cookies</li><li>数据库中未加密的凭证</li><li>或信息未移除</li>
                <li>绕过证书绑定</li><li>在未加密的通道上中转第三方数据</li><li>忽略SSL证书错误</li>
                <li>弱自定义Hostname Verifier</li><li>冗余授予的权限</li><li>用户认证时使用可欺骗的值，如IMEI,UDID</li><li>恶意Activity/Service调用</li>
                <li>Smbols Remnant</li>
                <li>不安全网络sockets的使用</li><li>服务端检查</li><li>应用程序没有或不当的会话管理</li>
                <li>跨域脚本攻击</li><li>详细的错误信息显示内部的敏感信息</li>
                <li>应用程序只能允许HTTP GET和POST方法</li><li>Cookies的路径属性设置检查</li>
                <li>点击劫持检查</li><li>服务器/OS的指纹识别</li>
                <li>绕过客户端检查</li><li>无效SSL证书</li><li>实现不当的或未实现的修改</li>
                <li>应用程序登出功能</li><li>密码功能</li><li>敏感信息作为查询字符</li>
                <li>弱密码策略</li><li>适当的超时保护</li><li>目录遍历</li><li>文件包含</li><li>Back-and-Refresh攻击</li>
                <li>目录浏览</li><li>开放式URL重定向</li><li>代码中不当的异常处理</li>
                <li>证书链没有被验证</li><li>未显示最近登录信息</li>
                <li>通过修改JAR文件进行UI模拟</li><li>到期后或释放的资源操作</li><li>缓存粉碎未激活</li><li>Android备份检查</li><li>App数据允许全局文件权限</li>
                <li>明文存储加密密钥或敏感信息</li><li>未实行可信任发布</li><li>允许所有Hostname Verifier</li><li>App/Web缓存敏感数据泄露</li><li>泄露Content Provider</li>
                <li>使用不安全的算法</li><li>本地文件包含</li><li>Broadcast盗窃</li><li>恶意Broadcast注入</li><li>弱Check-sum控制/改变检查</li>
                <li>Unix domain sockets的不安全权限</li><li>响应中包含明文密码</li><li>直接引入未认证的内部资源</li><li>CORS攻击</li>
                <li>服务端不当的输入验证</li><li>CSRF/SSRF检查</li><li>可缓存的HTTPS响应内容</li><li>Cookie的HttpOnly属性检查</li><li> Cookie的Secure属性检查</li>
              </ul>
              </div>
              {/* <div className="mainTest_logo"><img src="/static/services/web-3.png" alt="服务内容"/></div> */}
            </div>
        </div> 
        <div className="foot">
           <div className="foot_All">
              <div className="foot_one"><h1>服务经验</h1><h1>团队成员</h1><h1>成功案例</h1><h1>客户好评</h1></div>
              <div className="foot_two"><h1>10年</h1><h1>35</h1><h1>3023</h1><h1>5230</h1></div>
            </div>
        </div> 
        </div>
        
        <BackTop />
        <Footer id="footer_1_0" key="footer_1_0" isMode={this.state.isMode} />,
      </div>
    );
  }
}
