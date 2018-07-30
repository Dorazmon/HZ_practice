import React from 'react'
import ReactDOM from 'react-dom'
import enquire from 'enquire.js'
import { scrollScreen } from 'rc-scroll-anim'
import { BackTop, Affix, Row, Col } from 'antd' 
import Nav from '../../components/Nav';
import Footer from '../../components/codeFooter';



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
        <div className="code_ALL">
          <div className="code_content1">
              <div className="code_content1-text">
                <Row type="flex" align="middle">
                  <Col span={8} style={{textAlign: 'center'}}><img src="/static/application-security/web1.png" alt="图标" /></Col>
                  <Col span={16}><h1>网站安全云</h1></Col>
                </Row>
                <p>网站安全云平台是基于漏洞知识库，通过采集信息、执行漏洞检测脚本对指定的远程计算机系统、应用程序、数据库、WEB服务、网络设备、安全设备的安全脆弱性进行检测，发现可被利用漏洞和不安全配置的主动预警平台。</p>
              </div>
              <div className="code_content1-img">
                  <img src="/static/application-security/11.png" />
              </div>
          </div>
          {/* <div className="code_content2">
            <div className="code_content2-title">
              <h1>————&nbsp;&nbsp; 服务理念 &nbsp;&nbsp;————</h1>
                <h2>安全源于代码，趋势不可抵挡</h2>
                <div className="title-h2"><h2>——让代码安全更简单、更普惠</h2></div>
              </div>
            <div className="code_content2-text">
              <div className="code_content2_1">
                <p>存储、计算、监控、安全，我们提供业界领先水平的服务（测试内容）</p>
                <img src="/static/application-security/tu1.png" />
              </div>
              <div className="code_content2_2">
                <img src="/static/application-security/111.jpg" />
              </div>
            </div>
          </div> */}
          <div className="code_content3">
            <div className="code_content3-title">
              <h1>————&nbsp;&nbsp; 我们的优势 &nbsp;&nbsp;————</h1>
            </div>
            <div className="code_content3-text">
              <div className="code_content3_1">
                <div className="websecurity_content3_1_1">
                  <h1>全方位系统安全检测 </h1>
                  <p>支持对系统漏洞检测、网站安全监控、数据库漏洞检测、基线配置核查、弱口令检测等</p>
                </div>
                <div className="websecurity_content3_1_2">
                  <h1>全面资产扫描</h1>
                  <p>可扫描多种操作系统、应用程序、 虚拟化、网络及安全设备</p>
                </div>
              </div>
              <div className="code_content3_2">
                <img src="/static/application-security/tu20.png" />
              </div>
              <div className="code_content3_3">
                <div className="websecurity_content3_3_1">
                  <h1>工程漏洞检查</h1>
                  <p>支持多种工控协议、结合专业的工控漏洞库</p>
                </div>
                <div className="websecurity_content3_3_2">
                  <h1>等保合规检查</h1>
                  <p>支持等保合规性检查</p>
                </div>
                <div className="websecurity_content3_3_3">  
                  <h1>丰富的报告展示</h1>
                  <p>多纬度、多视角、多格式的报告展示</p>
                </div>
              </div>
            </div>
          </div>
          <div className="code_content4">
            <div className="code_content4-title">
              <h1>————&nbsp;&nbsp; 资产安全展示 &nbsp;&nbsp;————</h1>
            </div>
            <div className="code_content4-text">
              <div className="code_content4_1">
                  <img src="/static/application-security/web-1.png"/>
                  <p><h1>多维度扫描</h1></p>
                  <img src="/static/application-security/3.png" />
                  <p><h1>网站安全监控</h1></p>
                  <img src="/static/application-security/5.png" />
              </div>
              <div className="code_content4_2">
                <img src="/static/code-security/tu4.png" />
              </div>
              <div className="code_content4_3">                
                <p><h1>资产手动或自动配置</h1></p>
                <img src="/static/application-security/2.png"/>
                <p><h1>可定制化报表</h1></p>
                <img src="/static/application-security/4.png" />
                <p><h1>丰富的结果展示</h1></p>
              </div>
            </div>
          </div>
          <div className="code_content5">
            <div className="code_content5-title">
              <h1>————&nbsp;&nbsp; 支持功能 &nbsp;&nbsp;————</h1>
            </div>
            <div className="code_content5-text"> 
                <div className="code_content5_text_content">
                  <img src="/static/application-security/tu23.png" />
                  <h1>注入攻击</h1>
                </div>
                <div className="code_content5_text_content">
                  <img src="/static/application-security/tu24.png" />
                  <h1>跨站脚本(XSS)</h1>
                </div>
                <div className="code_content5_text_content">
                  <img src="/static/application-security/tu25.png" />
                  <h1>失效的认证和会话管理</h1>
                </div>
                <div className="code_content5_text_content">
                  <img src="/static/application-security/tu26.png" />
                  <h1>不安全的直接对象引用</h1>
                  </div>
                <div className="code_content5_text_content">
                  <img src="/static/application-security/tu27.png" />
                  <h1>跨站请求伪造</h1>
                </div>
                <div className="code_content5_text_content">
                  <img src="/static/application-security/tu28.png" />
                  <h1>安全配置错误</h1>
                </div>
                <div className="code_content5_text_content">
                  <img src="/static/application-security/tu29.png" />
                  <h1>限制URL访问失败</h1>
                </div>
                <div className="code_content5_text_content">
                  <img src="/static/application-security/tu31.png" />
                  <h1>尚未认证的重定向和转发</h1>
                </div>
                <div className="code_content5_text_content">
                  <img src="/static/application-security/tu30.png" />
                  <h1>不安全的密码储藏</h1>
                </div>
         
              </div>
          </div>
        </div>
        <BackTop />
        <Footer id="footer_1_0" key="footer_1_0" isMode={this.state.isMode} />
      </div>
    );
  }
}
