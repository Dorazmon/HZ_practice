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
        <div className="banner"><h1>等级保护</h1></div>
        <div className="Myservice" style={{height: '500px'}}>
          <div className="Myservice_All">
            <div className="Myservice_logo"><img src="/static/services/web-1.png" alt="服务概述" /></div>
            <div className="Myservice_Content">
              <h1>我们的服务</h1>
              <h2 className="xia">——————</h2>
              <p>信息安全等级保护制度是国家在国民经济和社会信息化的发展过程中，提高信息安全保障能力和水平，维护国家安全、社会稳定和公共利益，保障和促进信息化建设健康发展的一项基本制度。 孝道科技TCSEC团队根据国家相关政策要求，提炼并总结全面的等级保护建设模型，强调等级保护建设的三个重点，包括定级、建设整改以及等级测评，为客户构建覆盖全面、突出重点、节约成本、符合实际的等级化安全保障体系，为客户的业务活动提供充分的保障，根据等级保护实施指南给出的等级保护周期，孝道科技TCSEC团队提供等级保护工作各阶段的安全服务。</p>
            </div>
          </div>
        </div>

        <div className="mainContent">
        <div className="assessContent">
          <h1>服务内容</h1>
          <p>等级保护服务内容涵盖十大类，技术要求：物理安全、网络安全、主机安全、应用安全和数据安全；管理要求：安全管理制度、安全管理机构、人员安全管理、系统建设管理和系统运维管理，具体实施流程及工作如下所示：</p>      
        </div>
        </div>

        <div>
        <div id="nonFinancial">
        <div><img src="/static/services/Level_protection.png" alt="流程图" /></div>
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
