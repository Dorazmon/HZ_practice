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
        <Nav id="nav_0_0" key="nav_0_0" isMode={this.state.isMode} />
        <div className="web">
        <div className="banner"><h1>移动安全</h1></div>
        
        {/* <div className="mainContent">
        <div className="safety_assessment">
          <h1>用户需求</h1>
          <div style={{width: '400px', textAlign: 'center' , margin: '50px auto 0' }}><img src="/static/services/app1.png" style={{width: '400px'}}/></div>     
        </div>
        </div> */}

        <div className="mainContent">
        <div className="safety_assessment">
          {/* <h1 id="security-reinforce">解决方案</h1> */}
          <div className="app-security" >
            <h1 id="security-reinforce">APP安全加固</h1>
            {/* <h2 className="underline">—————</h2> */}
            <Row type="flex" align="middle">
              <Col span={12}><div className="app-img"><img src="/static/application-security/app-2.png" /></div></Col>
              <Col span={12}>
                <ul className="app_security_strong">
                  <li>SO加固<p>防止通过ida， readelf等工具对SO里面的逻辑进行分析，保护native代码。</p></li>
                  <li>Dex加壳<p>防止对java层代码的内存dump， 保护Java层代码。</p></li>
                  <li>Java指令翻译<p>通过修改Java层业务逻辑的调用关系链, 保护业务逻辑Java虚拟函数。</p></li>
                  <li id="security-component">全量混淆 <p>利用SO拦截技术和Java拦截技术，对Java代码进行全量混淆。</p></li>
                  <li>APP瘦身<p>通过全程序视角，对APK体积进行缩减。</p></li>
                </ul>
              </Col>
            </Row>
        </div>
          
          <div className="app-security" >
            <h1 id="security-component">APP安全组件</h1>
            <ul className="app_security_component">
                <li>
                  <Row type="flex" align="middle" justify="center">
                    <Col span={6}><img src="/static/application-security/app-4.png"/></Col>
                    <Col span={18} className="app_security_component_text">安全存储<p>实现用户数据的安全存储，保护用户的隐私数据不被泄漏。</p></Col>
                  </Row>
                </li>
                <li>
                  <Row type="flex" align="middle" justify="center">
                    <Col span={6}><img src="/static/application-security/app-5.png"/></Col>
                    <Col span={18} className="app_security_component_text">安全加密<p>开发者密钥的安全管理与加密算法实现，保证密钥的安全性和安全的加解密操作。</p></Col>
                  </Row>
                </li>
                <li>
                  <Row type="flex" align="middle" justify="center">
                    <Col span={6}><img src="/static/application-security/app-3.png"/></Col>
                    <Col span={18} className="app_security_component_text">安全签名<p>实现客户端请求的签名处理，保证客户端与服务端通信请求不被伪造。</p></Col>
                  </Row>
                </li>
                <li>
                  <Row type="flex" align="middle" justify="center">
                    <Col span={6}><img src="/static/application-security/app-6.png"/></Col>
                    <Col span={18} className="app_security_component_text">人机识别<p>识别机器软件自动化恶意行为。</p></Col>
                  </Row>
                </li>
                <li>
                  <Row type="flex" align="middle" justify="center">
                    <Col span={6}><img src="/static/application-security/app-7.png"/></Col>
                    <Col span={18} className="app_security_component_text">模拟器检测<p>实时检测模拟器环境，发现潜在的破解和调整风险。</p></Col>
                  </Row>
                </li>
               
            </ul>
          </div>
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
