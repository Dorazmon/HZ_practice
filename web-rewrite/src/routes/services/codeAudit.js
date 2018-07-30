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
        <div className="banner"><h1>数据隐私检测</h1></div>
        <div className="Myservice">
          <div className="Myservice_All">
            <div className="Myservice_logo"><img src="/static/services/web-1.png" alt="服务概述" /></div>
            <div className="Myservice_Content">
              <h1>我们的服务</h1>
              <h2 className="xia">——————</h2>
              <p>随着企业信息化的不断推进，数据已成为我们当今最重要的资产，其中敏感数据更是承载着企业的核心利益，是企业重点的保护对象。 然而在日常工作中，由于内部人员为了使用便利等原因违规存储或者敏感数据的定义随着时间和场合的不同而转变（非敏感数据转变为敏感数据）, 导致敏感数据遗留在缺少安全防护的存储区域内或传播于互联网。这些现象将造成敏感数据被随意调取或存在数据泄漏的高风险，是企业一颗随时引爆的未知“炸弹”。</p>
            </div>
          </div>
        </div>
        <div className="mainContent">
        <div className="assessContent">
          <h1>检测方法</h1>
          <p>孝道科技TCSEC团队的数据泄漏检测服务相当于专业的“排弹专家”，我们将为用户提供对存储在终端、服务器、应用系统、数据库及互联网上的海量数据进行快速检测和响应。</p>
        </div>
        </div>
        <div id="test_picture"><div><img src="/static/services/testMethod.jpg" alt="检测方法"/></div></div>


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
