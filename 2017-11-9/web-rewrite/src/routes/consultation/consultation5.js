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
        <div className="banner"><h1>信息科技风险管理</h1></div>
        <div className="Myservice">
          <div className="Myservice_All">
            <div className="Myservice_logo"><img src="/static/services/web-1.png" alt="服务概述" /></div>
            <div className="Myservice_Content">
              <h1>我们的服务</h1>
              <h2 className="xia">——————</h2>
              <p>孝道科技TCSEC团队可帮助金融行业用户根据《商业银行信息科技风险管理指引》、《非金融机构信息科技风险管理指引》等相关规范要求，内容包括信息科技治理、信息科技风险管理、信息安全、信息系统开发测试和维护、信息科技运行、业务连续性管理、外包、内部审计、外部审计等方面。我们可帮助用户全面了解信息科技风险管理现状，提出存在问题，并针对工作目标，给出改进建议，提高风险管理水平。</p>
            </div>
          </div>
        </div>

        <div>
        <div id="nonFinancial">
        <h1>服务内容</h1>
        <div><img src="/static/services/audit_method.png" alt="审计方法" /></div>
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
