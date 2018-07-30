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
        <div className="banner"><h1>非金融机构支付安全</h1></div><div className="Myservice">
          <div className="Myservice_All">
            <div className="Myservice_logo"><img src="/static/services/web-1.png" alt="服务概述" /></div>
            <div className="Myservice_Content">
              <h1>我们的服务</h1>
              <h2 className="xia">——————</h2>
              <p>根据中国人民银行的《非金融机构支付服务管理办法》、《非金融机构支付服务业务系统检测规范》（JR/T 0123-2014）等系列规范要求，孝道科技TCSEC团队从非金融机构支付服务业务系统功能、风险监控、性能、安全性、文档和外包六项检测类进行合规性的预前评估、全面整改咨询服务，以保障其支付业务处理系统、网络通信系统以及容纳上述系统的专用机房满足中国人民银行对支付服务业务系统的技术标准符合性和安全性要求。</p>
            </div>
          </div>
        </div>
        <div id="nonFinancial">
        <div><img src="/static/services/nonFinancial_gist.png" alt="依据" /></div>
        <div><img src="/static/services/nonFinancial_content.png" alt="非金融内容" /></div>
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
