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
        <div className="banner"><h1>PCI DSS数据安全标准</h1></div>
        <div className="Myservice">
          <div className="Myservice_All">
            <div className="Myservice_logo"><img src="/static/services/web-1.png" alt="服务概述" /></div>
            <div className="Myservice_Content">
              <h1>我们的服务</h1>
              <h2 className="xia">——————</h2>
              <p>支付卡行业数据安全标准（“PCI DSS”）对于所有涉及信用卡信息机构的安全方面作出标准的要求，其中包括安全管理、策略、过程、网络体系结构、软件设计要求的列表等，全面保障交易安全。PCI DSS适用于所有涉及支付卡处理的实体，包括商户、处理机构、购买者、发行商和服务提供商及储存、处理或传输持卡人资料的所有其他实体。PCI DSS包括一组保护持卡人信息的基本要求，并可能增加额外的管控措施，以进一步降低风险。孝道科技TCSEC团队为用户提供PCI DSS认证前的咨询服务。</p>
            </div>
          </div>
        </div>
        
        <div>
        <div id="nonFinancial">
        <h1>服务内容</h1>
        <div><img src="/static/services/PIC_content.png" alt="依据" /></div>
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
