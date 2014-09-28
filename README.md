Cresco-Agent-Dummy-Plugin
=========================

### Status
[![Build Status](http://128.163.188.129:9998/buildStatus/icon?job=Cresco-Agent-Dummy-Plugin)](http://128.163.188.129:9998/job/Cresco-Agent-Dummy-Plugin/)

---
### Install

1. Download/update/confirm avalability of the [Cresco-Agent](http://128.163.188.129:9998/job/Cresco-Agent/lastSuccessfulBuild/com.researchworx.cresco$cresco-agent/) 
2. Download the [Latest Build](http://128.163.188.129:9998/job/Cresco-Agent-Dummy-Plugin/lastSuccessfulBuild/com.researchworx.cresco$cresco-agent-dummy-plugin/) of the Cresco-Agent-Dummy-Plugin an place it in the plugin subdirectory of the agent directory. 
4. Modify _Cresco-Agent-Plugins.ini_ to configure and enable your plug-in.
5. Execute: java -jar  _cresco-agent-\<version\>.jar_ 

---

### Usage

1. SSH to the hostname of the controller server.
2. Login using account: _admin_ and password: _admin_ **(actually any account will work as long as username matches password, this must be corrected soon)**
3. Follow shell instructions.
4. Agent commands can be found under the name of the shell: _\> \<agent name\> \<command\>_

---

### Implements


* String getName() : Name of Plugin
* String getVersion() : Version of Plugin
* String getCommandSet() : Command-set of Plugin


###License

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

###Project lead

Cody Bumgardner (@codybum)
