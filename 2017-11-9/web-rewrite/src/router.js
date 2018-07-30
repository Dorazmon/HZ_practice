import React from 'react';
import { Router, Route,IndexRedirect } from 'dva/router';
import IndexPage from './routes/Home';
import CodeSecurity from './routes/code-security';// 代码安全云

import ApplicationSecurity from './routes/application-security';// 应用安全云
import WebSecurity from './routes/application-security/webSecurity';
import AppSecurity from './routes/application-security/appSecurity';


import Services from './routes/services';// 服务
import WebPenetration from './routes/services/webPenetration';
import AppPenetration from './routes/services/appPenetration';
import SafetyAssessment from './routes/services/safetyAssessment';
import DataPrivacy from './routes/services/dataPrivacy';
import Sdl from './routes/services/sdl';
import CloudSecurity from './routes/services/cloudSecurity';

import Consultation from './routes/consultation';// 咨询
import Consultation1 from './routes/consultation/consultation1';
import Consultation2 from './routes/consultation/consultation2';
import Consultation3 from './routes/consultation/consultation3';
import Consultation4 from './routes/consultation/consultation4';
import Consultation5 from './routes/consultation/consultation5';

import About from './routes/about';
import Us from './routes/about/us';
// import Bussiness from './routes/about/bussiness';
import Recruitment from './routes/about/recruitment';// 招聘
import Anniversary from './routes/anniversary';// 两周年
import Expected from './routes/expected';
import NotFoundPage from './routes/notFoundPage';

function RouterConfig({ history }) {
  return (
    <Router history={history}>
      <Route path="/" component={IndexPage} />
      <Route path="/anniversary" component={Anniversary} />
      <Route path="/code-security" component={CodeSecurity} />

      <Route path="/application-security" component={ApplicationSecurity} >
        <IndexRedirect to="web-security" />
        <Route path="web-security" component={WebSecurity} />
        <Route path="app-security" component={AppSecurity} />
      </Route>

      <Route path="/services" component={Services} >
        <IndexRedirect to="web-penetration" />
        <Route path="web-penetration" component={WebPenetration} />
        <Route path="app-penetration" component={AppPenetration} />
        <Route path="safety-assessment" component={SafetyAssessment} />
        <Route path="data-privacy" component={DataPrivacy} />
        <Route path="cloud-security" component={CloudSecurity} />
      </Route>
      <Route path="/sdl" component={Sdl} />
      <Route path="/consultation" component={Consultation} >
        <IndexRedirect to="thirdpay-security" />
        <Route path="thirdpay-security" component={Consultation1} />
        <Route path="pci-dss" component={Consultation2} />
        <Route path="isms" component={Consultation3} />
        <Route path="sgp" component={Consultation4} />
        <Route path="itrm" component={Consultation5} />
      </Route>

      <Route path="/about" component={About} >
        <IndexRedirect to="/us" />
        <Route path="us" component={Us} />
        {/* <Route path="bussiness" component={Bussiness} /> */}
        <Route path="recruitment" component={Recruitment} />
      </Route>
      <Route path="/expected" component={Expected}/>
      <Route path="*" component={NotFoundPage} />
    </Router>
  );
}

export default RouterConfig;
