"use strict";(self.webpackChunkdispatch=self.webpackChunkdispatch||[]).push([[359],{9459:(e,t,n)=>{n.r(t),n.d(t,{assets:()=>l,contentTitle:()=>r,default:()=>u,frontMatter:()=>s,metadata:()=>a,toc:()=>p});var i=n(7462),o=(n(7294),n(3905));n(8209);const s={id:"dispatch-test-junit5",title:"Test JUnit5",sidebar_label:"Test JUnit5"},r=void 0,a={unversionedId:"modules/dispatch-test-junit5",id:"modules/dispatch-test-junit5",title:"Test JUnit5",description:"Features",source:"@site/docs/modules/dispatch-test-junit5.md",sourceDirName:"modules",slug:"/modules/dispatch-test-junit5",permalink:"/Dispatch/docs/next/modules/dispatch-test-junit5",draft:!1,editUrl:"https://github.com/rbusarow/Dispatch/edit/main/website/docs/modules/dispatch-test-junit5.md",tags:[],version:"current",frontMatter:{id:"dispatch-test-junit5",title:"Test JUnit5",sidebar_label:"Test JUnit5"},sidebar:"Docs",previous:{title:"Test JUnit4",permalink:"/Dispatch/docs/next/modules/dispatch-test-junit4"}},l={},p=[{value:"Features",id:"features",level:2},{value:"CoroutineTest example",id:"coroutinetest-example",level:3},{value:"CoroutineTestExtension example",id:"coroutinetestextension-example",level:3},{value:"Setting Dispatchers.Main",id:"setting-dispatchersmain",level:2},{value:"This module replaces dispatch-test",id:"this-module-replaces-dispatch-test",level:2},{value:"JUnit dependencies",id:"junit-dependencies",level:2},{value:"Minimum Gradle Config",id:"minimum-gradle-config",level:3},{value:"JUnit 4 interoperability",id:"junit-4-interoperability",level:3}],c={toc:p};function u(e){let{components:t,...n}=e;return(0,o.kt)("wrapper",(0,i.Z)({},c,n,{components:t,mdxType:"MDXLayout"}),(0,o.kt)("h2",{id:"features"},"Features"),(0,o.kt)("p",null,"In addition to all the functionality in ",(0,o.kt)("a",{parentName:"p",href:"https://rbusarow.github.io/Dispatch/api/dispatch-test/dispatch.test/index.html"},"dispatch-test"),", this module exposes a JUnit\n5 ",(0,o.kt)("a",{parentName:"p",href:"https://rbusarow.github.io/Dispatch/api/dispatch-test-junit5/dispatch.test/-coroutine-test-extension/index.html"},"CoroutineTestExtension")," and ",(0,o.kt)("a",{parentName:"p",href:"https://rbusarow.github.io/Dispatch/api/dispatch-test-junit5/dispatch.test/-coroutine-test/index.html"},"CoroutineTest")," annotation to handle set-up and tear-down of\na ",(0,o.kt)("a",{parentName:"p",href:"https://rbusarow.github.io/Dispatch/api/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.html"},"TestProvidedCoroutineScope"),"."),(0,o.kt)("p",null,"Since ",(0,o.kt)("a",{parentName:"p",href:"https://rbusarow.github.io/Dispatch/api/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.html"},"TestProvidedCoroutineScope")," is a ",(0,o.kt)("a",{parentName:"p",href:"https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/-test-coroutine-scope/index.html"},"TestCoroutineScope"),", this Extension also\ninvokes ",(0,o.kt)("a",{parentName:"p",href:"https://rbusarow.github.io/Dispatch/api/dispatch-test/dispatch.test/-test-provided-coroutine-scope/index.html#kotlinx.coroutines.test/TestCoroutineScope/cleanupTestCoroutines/#/PointingToDeclaration/"},"cleanupTestCoroutines")," after the test."),(0,o.kt)("h3",{id:"coroutinetest-example"},"CoroutineTest example"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-kotlin"},"@CoroutineTest(CustomScopeFactory::class)\nclass SomeClassTest(\n  val testScope: TestProvidedCoroutineScope\n) {\n\n  @Test\n  fun `some test`() = runBlocking {\n\n    val subject = SomeClass(testScope)\n\n    val job = subject.fireAndForget()\n\n    // TODO: assertions go here\n  }\n\n}\n\nclass SomeClass(val coroutineScope: CoroutineScope) {\n  fun fireAndForget() = launch { }\n}\n\nclass CustomScopeFactory : CoroutineTestExtension.ScopeFactory() {\n  override fun create() = TestProvidedCoroutineScope(context = Job())\n}\n")),(0,o.kt)("h3",{id:"coroutinetestextension-example"},"CoroutineTestExtension example"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-kotlin"},"class SomeClassTest {\n\n  @JvmField\n  @RegisterExtension\n  val extension = CoroutineTestExtension()\n\n  @Test\n  fun `some test`(scope: TestProvidedCoroutineScope) = runBlocking {\n\n    val subject = SomeClass(scope)\n\n    val job = subject.fireAndForget()\n\n    // TODO: assertions go here\n  }\n\n}\n\nclass SomeClass(val coroutineScope: CoroutineScope) {\n  fun fireAndForget() = launch { }\n}\n")),(0,o.kt)("h2",{id:"setting-dispatchersmain"},"Setting Dispatchers.Main"),(0,o.kt)("p",null,"Even though ",(0,o.kt)("inlineCode",{parentName:"p"},"dispatch-core")," eliminates the need to use ",(0,o.kt)("inlineCode",{parentName:"p"},"Dispatchers.Main")," in internal code, it\u2019s\nstill possible that code which has yet to be migrated, or a third-party library is making use of the\nhard-coded dispatcher. Because of this, the extension still calls ",(0,o.kt)("inlineCode",{parentName:"p"},"Dispatchers.setMain(...)")," in its\nsetup and ",(0,o.kt)("inlineCode",{parentName:"p"},"Dispatchers.resetMain()")," afterwards."),(0,o.kt)("h2",{id:"this-module-replaces-dispatch-test"},"This module replaces dispatch-test"),(0,o.kt)("p",null,"If using this module, there is no need to include the ",(0,o.kt)("inlineCode",{parentName:"p"},"dispatch-test")," artifact in your dependencies."),(0,o.kt)("h2",{id:"junit-dependencies"},"JUnit dependencies"),(0,o.kt)("h3",{id:"minimum-gradle-config"},"Minimum Gradle Config"),(0,o.kt)("p",null,"Because this is a JUnit 5 Extension, it requires a the JUnit 5 artifact. No external libraries are\nbundled as part of Dispatch, so you\u2019ll need to add it to your ",(0,o.kt)("inlineCode",{parentName:"p"},"dependencies")," block yourself."),(0,o.kt)("ul",null,(0,o.kt)("li",{parentName:"ul"},(0,o.kt)("inlineCode",{parentName:"li"},"org.junit.jupiter:junit-jupiter:5.6.2"))),(0,o.kt)("p",null,"Add to your module's ",(0,o.kt)("inlineCode",{parentName:"p"},"build.gradle.kts"),":"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-kotlin"},'repositories {\n  mavenCentral()\n}\n\ndependencies {\n\n  // core\n  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")\n  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")\n  implementation("com.rickbusarow.dispatch:dispatch-core:1.0.0-beta10-SNAPSHOT")\n\n  // the junit5 artifact also provides the dispatch-test artifact\n  testImplementation("com.rickbusarow.dispatch:dispatch-test-junit5:1.0.0-beta10-SNAPSHOT")\n  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")\n  testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")\n}\n')),(0,o.kt)("h3",{id:"junit-4-interoperability"},"JUnit 4 interoperability"),(0,o.kt)("p",null,"Junit 4 provides a \u201cvintage\u201d (JUnit 4) artifact for legacy support (such as Robolectric or Android\ninstrumented tests). Dispatch also supports running both in the same project by just adding both\nartifacts."),(0,o.kt)("p",null,"Add to your module's ",(0,o.kt)("inlineCode",{parentName:"p"},"build.gradle.kts"),":"),(0,o.kt)("pre",null,(0,o.kt)("code",{parentName:"pre",className:"language-kotlin"},'repositories {\n  mavenCentral()\n}\n\ndependencies {\n\n  // core\n  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")\n  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")\n  implementation("com.rickbusarow.dispatch:dispatch-core:1.0.0-beta10-SNAPSHOT")\n\n  // the junit4 and junit5 artifacts also provides the dispatch-test artifact\n  testImplementation("com.rickbusarow.dispatch:dispatch-test-junit4:1.0.0-beta10-SNAPSHOT")\n  testImplementation("com.rickbusarow.dispatch:dispatch-test-junit5:1.0.0-beta10-SNAPSHOT")\n  testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")\n  testImplementation("org.junit.vintage:junit-vintage-engine:5.7.0")\n  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")\n}\n')))}u.isMDXComponent=!0},3905:(e,t,n)=>{n.d(t,{Zo:()=>c,kt:()=>m});var i=n(7294);function o(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function s(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);t&&(i=i.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,i)}return n}function r(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?s(Object(n),!0).forEach((function(t){o(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):s(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function a(e,t){if(null==e)return{};var n,i,o=function(e,t){if(null==e)return{};var n,i,o={},s=Object.keys(e);for(i=0;i<s.length;i++)n=s[i],t.indexOf(n)>=0||(o[n]=e[n]);return o}(e,t);if(Object.getOwnPropertySymbols){var s=Object.getOwnPropertySymbols(e);for(i=0;i<s.length;i++)n=s[i],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(o[n]=e[n])}return o}var l=i.createContext({}),p=function(e){var t=i.useContext(l),n=t;return e&&(n="function"==typeof e?e(t):r(r({},t),e)),n},c=function(e){var t=p(e.components);return i.createElement(l.Provider,{value:t},e.children)},u={inlineCode:"code",wrapper:function(e){var t=e.children;return i.createElement(i.Fragment,{},t)}},d=i.forwardRef((function(e,t){var n=e.components,o=e.mdxType,s=e.originalType,l=e.parentName,c=a(e,["components","mdxType","originalType","parentName"]),d=p(n),m=o,h=d["".concat(l,".").concat(m)]||d[m]||u[m]||s;return n?i.createElement(h,r(r({ref:t},c),{},{components:n})):i.createElement(h,r({ref:t},c))}));function m(e,t){var n=arguments,o=t&&t.mdxType;if("string"==typeof e||o){var s=n.length,r=new Array(s);r[0]=d;var a={};for(var l in t)hasOwnProperty.call(t,l)&&(a[l]=t[l]);a.originalType=e,a.mdxType="string"==typeof e?e:o,r[1]=a;for(var p=2;p<s;p++)r[p]=n[p];return i.createElement.apply(null,r)}return i.createElement.apply(null,n)}d.displayName="MDXCreateElement"},8209:(e,t,n)=>{n(7294)}}]);