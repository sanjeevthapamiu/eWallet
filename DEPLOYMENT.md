# eWallet Deployment to AWS EKS

* Login to AWS

* Go to Security Credientials <br/>
  ![AWS Security Credentials](https://github.com/sanjeevthapamiu/eWallet/blob/main/screenshots/aws-security-credentials.jpg)

* Create Access Keys <br/>
  ![AWS Access Keys](https://github.com/sanjeevthapamiu/eWallet/blob/main/screenshots/aws-access-keys.png)

* Choose “Command Line Interface (CLI)” option for interface: <br/>
  ![AWS CLI Option](https://github.com/sanjeevthapamiu/eWallet/blob/main/screenshots/aws-cli-option.png)

* Give description tag value (optional)

* Create access key and retrieve it <br/>
  ![AWS Retrieve Access Keys](https://github.com/sanjeevthapamiu/eWallet/blob/main/screenshots/aws-retrieve-access-keys.png)

* Download AWS CLI based on your Operating System by following instructions on this link:
  https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html

* After installing AWS CLI on your machine, go to your terminal and write: <br/>
  `aws configure` <br/>
  Put the access key and access secret key that we retrieved earlier <br/>
  ![AWS Configure](https://github.com/sanjeevthapamiu/eWallet/blob/main/screenshots/aws-configure.png)

* Install or update `kubectl` by following the instructions on this link: <br/>
  https://docs.aws.amazon.com/eks/latest/userguide/install-kubectl.html#_step_1_check_if_kubectl_is_installed

* Install `eksctkl` by following instructions on this link:
  https://eksctl.io/installation

* Create cluster using eksctl: <br/>
  `eksctl create cluster --name <<my-cluster >> --region <<region-code>> --version 1.31 --nodes=1 --node-type=t2.small` <br/>
  ![AWS ekstcl Cluster](https://github.com/sanjeevthapamiu/eWallet/blob/main/screenshots/aws-ekstcl-cluster.png)

* Configure Kubernetes Configuration: <br/>
  `aws eks --region <<region-code>> update-kubeconfig --name <<my-cluster >>` <br/>
  ![AWS Configure Kubernetes Configuration](https://github.com/sanjeevthapamiu/eWallet/blob/main/screenshots/aws-configure-kubernetes-configuration.png)

* Go inside the project folder in the terminal <br/>
  Run Deployment, Secrets and ConfigMap on AWS EKS: <br/>
  `kubectl apply -f <<deployment>>.yml` <br/>
  ![AWS kubectl Apply Deployment](https://github.com/sanjeevthapamiu/eWallet/blob/main/screenshots/aws-kubectl-apply-deployment.png)

* List all the running pods: <br/>
  `kubectl get pods` <br/>
  ![AWS kubectl Running Pods](https://github.com/sanjeevthapamiu/eWallet/blob/main/screenshots/aws-kubectl-pods.png)

* List all the services and to access endpoint to the app: <br/>
  `kubectl get svc` <br/>
  ![AWS kubectl Running Services](https://github.com/sanjeevthapamiu/eWallet/blob/main/screenshots/aws-kubectl-services.png)

* Delete deployment file: <br/>
  `kubectl delete -f <<deployment>>.yml` <br/>
  ![AWS kubectl Delete Deployment 1](https://github.com/sanjeevthapamiu/eWallet/blob/main/screenshots/aws-kubectl-delete-deployment-1.png) <br/>
  ![AWS kubectl Delete Deployment 2](https://github.com/sanjeevthapamiu/eWallet/blob/main/screenshots/aws-kubectl-delete-deployment-2.png)

* Delete cluster and nodes: <br/>
  `eksctl delete cluster –name <<my-cluster>> --region <<region-code>>`