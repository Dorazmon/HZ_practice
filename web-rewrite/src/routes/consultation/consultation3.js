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
        <div className="banner"><h1>信息安全管理体系</h1></div>
        <div className="Myservice" style={{height:'500px'}}>
          <div className="Myservice_All">
            <div className="Myservice_logo"><img src="/static/services/web-1.png" alt="服务概述" /></div>
            <div className="Myservice_Content">
              <h1>我们的服务</h1>
              <h2 className="xia">——————</h2>
              <p>信息安全管理体系是组织整体管理体系的一个部分，是基于风险评估建立、实施、运行、监视、评审、保持和持续改进信息安全等一系列的管理活动，是组织在整体或特定范围内建立信息安全方针和目标，以及完成这些目标所用的方法体系。GB/T22080/ISO/IEC27001是建立和维护信息安全管理体系的标准，它要求组织通过一系列的过程，如确定信息安全管理体系范围，制定信息安全方针和策略，明确管理职责，以风险评估为基础选择控制目标和控制措施等，确保组织达到动态、系统、全员参与、制度化并以预防为主的信息安全管理方式。</p>
            </div>
          </div>
        </div>

        <div className="mainContent">
        <div className="assessContent">
          <h1>服务内容</h1>
          <p>孝道科技TCSEC团队将 ISMS 和 ITSMS 体系有效的融合，为企业提供高效、优质、可落地的整体信息安全管理体系咨询服务。</p>      
        </div>
        </div>

        <div>
        <div id="nonFinancial">
        <div><img src="/static/services/Security_infor.png" alt="依据" /></div>
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
