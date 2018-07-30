import React from 'react';
import { Router, Route } from 'dva/router';
import IndexPage from './routes/Home';
import CodeSecurity from './routes/code-security';
import Anniversary from './routes/anniversary';// 两周年

import Services from './routes/services';// 服务
import WebPenetration from './routes/services/webPenetration';
import AppPenetration from './routes/services/appPenetration';
import SafetyAssessment from './routes/services/safetyAssessment';
import CodeAudit from './routes/services/codeAudit';
import Sdl from './routes/services/sdl';
import CloudSecurity from './routes/services/cloudSecurity';

import Consultation from './routes/consultation';
import Consultation1 from './routes/consultation/consultation1';
import Consultation2 from './routes/consultation/consultation2';
import Consultation3 from './routes/consultation/consultation3';
import Consultation4 from './routes/consultation/consultation4';
import Consultation5 from './routes/consultation/consultation5';

import About from './routes/about';
import Recruitment from './routes/recruitment';
import NotFoundPage from './routes/notFoundPage';

function RouterConfig({ history }) {
  return (
    <Router history={history}>
      <Route path="/" component={IndexPage} />
      <Route path="/anniversary" component={Anniversary} />
      <Route path="/code-security" component={CodeSecurity} />
      <Route path="/services" component={Services} >
        <Route path="web-penetration" component={WebPenetration} />
        <Route path="app-penetration" component={AppPenetration} />
        <Route path="safety-assessment" component={SafetyAssessment} />
        <Route path="code-audit" component={CodeAudit} />
        <Route path="sdl" component={Sdl} />
        <Route path="cloud-security" component={CloudSecurity} />
      </Route>
      <Route path="/consultation" component={Consultation} >
        <Route path="consultation1" component={Consultation1} />
        <Route path="consultation2" component={Consultation2} />
        <Route path="consultation3" component={Consultation3} />
        <Route path="consultation4" component={Consultation4} />
        <Route path="consultation5" component={Consultation5} />
      </Route>
      <Route path="/about" component={About} />
      <Route path="/recruitment" component={Recruitment} />
      <Route path="*" component={NotFoundPage} />
    </Router>
  );
}

export default RouterConfig;
