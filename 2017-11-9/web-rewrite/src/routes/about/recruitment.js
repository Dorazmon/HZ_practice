import React from 'react'
import ReactDOM from 'react-dom'
import enquire from 'enquire.js'
import { scrollScreen } from 'rc-scroll-anim'
import { BackTop, Affix, Row, Col } from 'antd' 
import Nav from '../../components/Nav'
import Footer from '../../components/Footer'

export default class User extends React.Component {
  constructor(props) { super(props); this.state = { isMode: false, }; } 
  componentDidMount() {    // 适配手机屏幕;    
    this.enquireScreen((isMode) => { this.setState({ isMode }); });
  }
  enquireScreen = (cb) => {    /* eslint-disable no-unused-expressions */
    enquire.register('only screen and (min-width: 320px) and (max-width: 767px)',{
      match: () => { cb && cb(true); }, unmatch: () => { cb && cb(); }, 
    });
            /* eslint-enable no-unused-expressions */
    }
  render() {
    return (<div className="templates-wrapper">
        <Nav id="nav_0_0" key="nav_0_0" isMode={this.state.isMode} />,
        <div style={{ textAlign: 'center' }} className="recrument">
            <img src="/static/services/security.jpg"/>
        </div>
        <BackTop />
        <Footer id="footer_1_0" key="footer_1_0" isMode={this.state.isMode} />,
                </div>);
    }
}
