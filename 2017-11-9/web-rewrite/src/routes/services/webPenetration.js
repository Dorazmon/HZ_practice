import React from 'react'
import ReactDOM from 'react-dom'
import enquire from 'enquire.js'
import { scrollScreen } from 'rc-scroll-anim'
import { BackTop, Affix, Row, Col } from 'antd' 
import Nav from '../../components/Nav';
import Footer from '../../components/Footer';
import './web.less';

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
        {/* 导航栏 */}
        <Nav id="nav_0_0" key="nav_0_0" isMode={this.state.isMode} />
        {/* 页面内容 */}
        <div className="web" style={{marginTop: ' 0px' }}>
          {/* 标题 */}
        <div className="banner"><h1>Web渗透测试</h1></div>
        <div className="Myservice">
          <div className="Myservice_All">
            <div className="Myservice_logo"><img src="/static/services/web-1.png" alt="服务概述" /></div>
            <div className="Myservice_Content">
              <h1>我们的服务</h1>
              <h2 className="xia">——————</h2>
              <p>针对用户的Web渗透测试服务，孝道科技TCSEC团队在经用户授权许可的前提下,以模拟黑客可能使用的攻击和漏洞发现技术，对目标Web系统进行渗透测试安全验证,采用可控制、非破坏性质的方法和手段发现目标及存在弱点，帮助用户发现所面临的安全风险。</p>
            </div>
          </div>
        </div>

        <div className="mainTest">
            <div className="mainTest_ALL">
              <div className="mainTest_content_app"> <h1>检测要点</h1>
                <h2 className="xia">——————</h2>
                <ul className="testContent_app" id="outer">
                <li>SQL注入攻击</li><li>反射式跨站脚本攻击</li><li>存储式跨站脚本攻击</li><li>会话固定</li><li>HTTP头安全配置</li>
                <li>HTTP方法</li><li>水平权限溢出</li><li>纵向权限溢出</li><li>弱密码规则测试</li><li>账户锁定测试</li><li>安全问题测试</li><li>目录遍历</li><li>文件包含</li>
                <li>授权绕过</li><li>Cookie属性检查</li><li>会话管理检查</li><li>会话超时测试</li><li>账户恶意锁定</li><li>用户账户唯一性测试</li><li>CRLF注入测试</li><li>堆溢出</li><li>XML注入测试</li><li>Xpath注入测试</li><li>LDAP注入测试</li>
                <li>远程文件包含</li><li>缓冲区溢出</li><li>本地文件包含</li><li>栈溢出</li><li>HTTP Split测试</li><li>代码异常处理</li><li>栈异常跟踪</li><li>PaddingOracle测试</li><li>敏感信息泄露</li><li>上传文件测试</li><li>HTML注入</li>
                <li>URL重定向攻击</li><li>CSS注入攻击</li><li>点击劫持测试</li><li>WebSocket测试</li><li>本地存储测试</li><li>IMAP/SMTP注入测试</li><li>代码注入测试</li><li>处理响应时间测试</li><li>CORS测试</li><li>FlashSWF测试</li>
                <li>恶意文件上传测试</li><li>传输层常见安全测试</li><li>错误代码分析</li><li>失效验证测试</li><li>未验证重定向与转发</li><li>不安全对象失效间接引用</li><li>默认配置安全测试</li><li>邮件头注入测试</li><li>HTML5安全测试</li><li>常见框架已知脆弱性检查</li><li>ORM注入测试</li>
                <li>SSRF服务器端请求伪造测试</li><li>命令执行</li><li>DOM跨站脚本攻击</li><li>格式化字符串测试</li><li>Bind注入测试</li><li>服务端版本信息</li><li>SSL/TLS加密算法测试</li><li>Web Messaging测试</li><li>默认凭证</li><li>事务完整性测试</li><li>可用性测试</li><li>密码修改测试</li>
                <li>登出测试</li><li>暴力测试</li><li>权限溢出</li><li>常见框架已知脆弱性检查</li><li>使用已知不安全组件测试</li><li>Session随机性与唯一性测试</li><li>用户隐私信息检查</li>
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
        {/* 底部的footer */}
        <BackTop />
        <Footer id="footer_1_0" key="footer_1_0" isMode={this.state.isMode} />
      </div>
    );
  }
}
